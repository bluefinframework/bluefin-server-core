package cn.saymagic.controllers;

import cn.saymagic.config.Constants;
import cn.saymagic.entity.BaseWrapper;
import cn.saymagic.rx.transformer.MappingSaveTransformer;
import cn.saymagic.services.FileServices;
import cn.saymagic.util.FileUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import rx.schedulers.Schedulers;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by saymagic on 16/6/3.
 */
@RestController
@RequestMapping("/api/v1/")
public class AppController {

    @Autowired
    private FileServices mFileService;

    @RequestMapping(value = "/upload", method = {RequestMethod.POST, RequestMethod.PUT})
    public DeferredResult<Map<String, Object>> doUploadFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "mapping", required = false) MultipartFile mapping) {
        DeferredResult<Map<String, Object>> deferredResult = new DeferredResult<Map<String, Object>>();
        mFileService.handleNewFile(file)
                .compose(new MappingSaveTransformer(mapping, mFileService))
                .subscribeOn(Schedulers.io())
                .<BaseWrapper>subscribe(result -> deferredResult.setResult(result.toMap()),
                        error -> deferredResult.setErrorResult(error));
        return deferredResult;
    }

    @ResponseBody
    @RequestMapping(value = "/download/{app}/{version}", method = {RequestMethod.GET})
    public void doGetApkFile(@PathVariable("app") String app, @PathVariable("version") String version, HttpServletResponse response) throws IOException {
        mFileService.getFileInputStream(app, version)
                .toBlocking()
                .subscribe(file -> FileUtil.writeFileToHttpServletResponse(file, response),
                        error -> response.setStatus(HttpStatus.SC_NOT_FOUND));
    }


    @RequestMapping(value = "/apks/", method = {RequestMethod.GET})
    public
    @ResponseBody
    DeferredResult<String> doGetAllApks() {
        DeferredResult<String> deferredResult = new DeferredResult<String>();
        mFileService.doGetAllApkSimpleInfos()
                .subscribeOn(Schedulers.io())
                .<BaseWrapper>subscribe(result -> deferredResult.setResult(String.valueOf(result)),
                        error -> deferredResult.setErrorResult(error));
        return deferredResult;
    }


}

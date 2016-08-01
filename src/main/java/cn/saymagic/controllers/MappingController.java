package cn.saymagic.controllers;

import cn.saymagic.config.Constants;
import cn.saymagic.error.GlobalError;
import cn.saymagic.services.MappingService;
import cn.saymagic.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import rx.Observable;
import rx.schedulers.Schedulers;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Created by saymagic on 16/6/2.
 */
@RestController
@RequestMapping("/api/v1/")
public class MappingController {

    @Autowired
    private MappingService mMappingService;

    @ResponseBody
    @RequestMapping(value = "/mapping/{package}/{identify}", method = {RequestMethod.POST})
    public DeferredResult<String> doParsingMappingString(@PathVariable("package") String packageName, @PathVariable("identify") String version, @RequestBody String cipher) throws IOException {
        DeferredResult<String> deferredResult = new DeferredResult<String>();
        Observable<String> observable;
        if (TextUtil.isNotEmpty(cipher)) {
           observable = mMappingService.doParsingString(cipher, packageName, version);
        } else {
            observable = Observable.error(new GlobalError(GlobalError.PARAMETER_ERROR));
        }
        observable
                .subscribeOn(Schedulers.io())
                .subscribe((result) -> deferredResult.setResult(result),
                (error) -> deferredResult.setErrorResult(error));
        return deferredResult;
    }


}

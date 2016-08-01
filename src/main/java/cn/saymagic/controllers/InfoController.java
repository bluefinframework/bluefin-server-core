package cn.saymagic.controllers;

import cn.saymagic.config.Constants;
import cn.saymagic.services.FileServices;
import cn.saymagic.services.InfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by saymagic on 16/5/21.
 */
@RestController
@RequestMapping("/api/v1/")
public class InfoController {

    private static final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Autowired
    private InfoService mInfoService;

    @RequestMapping(value = "/info/{app}", method = {RequestMethod.GET})
    public DeferredResult<String> getLastInfo(@PathVariable("app") String app) {
        DeferredResult result = new DeferredResult();
        mInfoService.getLastestInfo(app)
                .subscribeOn(Schedulers.io())
                .subscribe(s -> result.setResult(s),
                        error -> result.setErrorResult(error));
        return result;
    }

    @RequestMapping(value = "/info/{app}/{identify}", method = {RequestMethod.GET})
    public DeferredResult<String> getInfo(@PathVariable("app") String app, @PathVariable("identify") String identify) {
        DeferredResult result = new DeferredResult();
        mInfoService.getAppInfo(app, identify)
                .subscribeOn(Schedulers.io())
                .subscribe(s -> result.setResult(s),
                        error -> result.setErrorResult(error));
        return result;
    }

    @RequestMapping(value = "/list/{app}", method = {RequestMethod.GET})
    public @ResponseBody
    DeferredResult<String> getAllVersionInfos(@PathVariable("app") String app) {
        DeferredResult<String> result = new DeferredResult();
        mInfoService.listAllFileInfos(app)
                .subscribeOn(Schedulers.io())
                .subscribe(s -> result.setResult(s.toString()),
                        error -> result.setErrorResult(error));
        return result;
    }

}

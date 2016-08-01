package cn.saymagic.controllers;

import cn.saymagic.services.ServerlLogService;
import cn.saymagic.util.FileUtil;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by saymagic on 16/6/9.
 */
@RequestMapping("/api/v1/")
@RestController
public class LogController {

    @Autowired
    private ServerlLogService mServerLogService;

    @RequestMapping(value = "/log/", method = {RequestMethod.GET})
    public void doGetServerLogFile(HttpServletResponse response) throws IOException {
        mServerLogService.getServerLogFile()
                .toBlocking()
                .subscribe(file -> FileUtil.writeFileToHttpServletResponse(file, response),
                        error -> response.setStatus(HttpStatus.SC_NOT_FOUND));
    }

}

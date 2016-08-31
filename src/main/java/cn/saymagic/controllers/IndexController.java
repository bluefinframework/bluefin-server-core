package cn.saymagic.controllers;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by saymagic on 16/7/16.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/index.html")
    public String indexHtml() {
        return "index";
    }


    @RequestMapping("/api/v1/ping")
    public
    @ResponseBody
    String ping(HttpServletRequest request) {
        return getPingInfo(request).toString();
    }

    public JSONObject getPingInfo(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestip", request.getRemoteAddr());
        jsonObject.put("serverip", request.getLocalAddr());
        jsonObject.put("timemillis", System.currentTimeMillis());
        return jsonObject;
    }

}

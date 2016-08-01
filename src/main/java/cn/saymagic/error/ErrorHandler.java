package cn.saymagic.error;

import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by saymagic on 16/5/9.
 * The global controller advice for handling errors and returning appropriate JSON.
 */
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(GlobalError.class)
    public @ResponseBody ModelAndView dogePoolGenericHandler(GlobalError e, HttpServletResponse response) {
        response.setStatus(e.getHttpStatus());
        return new ModelAndView(new MappingJackson2JsonView(), e.toMap());
    }



}

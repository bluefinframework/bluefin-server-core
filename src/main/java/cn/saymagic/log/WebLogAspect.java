package cn.saymagic.log;

import cn.saymagic.entity.WebLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Order(5)
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, WebLog> sessionLogMap = new HashMap<>();

    @Pointcut("execution(public * cn.saymagic.controllers..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        WebLog webLog = new WebLog(attributes.getSessionId());
        HttpServletRequest request = attributes.getRequest();
        JSONObject object = new JSONObject();
        object.put("URL", request.getRequestURL().toString());
        object.put("HTTP_METHOD" , request.getMethod());
        object.put("IP" , request.getRemoteAddr());
        object.put("CLASS_METHOD" , joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        object.put("ARGS " , Arrays.toString(joinPoint.getArgs()));
        webLog.setRequest(object.toString());
        sessionLogMap.put(attributes.getSessionId(), webLog);
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        WebLog webLog = sessionLogMap.remove(attributes.getSessionId());
        if (webLog == null) {
            logger.info("bad travel : " + ret.toString());
            return;
        }
        if (ret instanceof DeferredResult) {
            DeferredResult result = (DeferredResult) ret;
            result.onCompletion(() -> {
                if (result.getResult() != null) {
                    webLog.setResponse(result.getResult());
                    logger.info("succeed travel : " + webLog.toString());
                    return;
                } else {
                    logger.info("bad travel : " + ret.toString());
                    return;
                }
            });
        } else {
            webLog.setResponse(ret);
            logger.info("succeed travel : " + webLog.toString());
        }
    }


}
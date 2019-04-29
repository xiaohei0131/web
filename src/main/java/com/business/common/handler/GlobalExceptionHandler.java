package com.business.common.handler;

import com.alibaba.fastjson.JSONObject;
import com.business.common.BSResult;
import com.business.common.exception.ExceptionEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 统一捕捉shiro的异常，返回给前台一个json信息，前台根据这个信息显示对应的提示，或者做页面的跳转。
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //不满足@RequiresGuest注解时抛出的异常信息
    private static final String GUEST_ONLY = "Attempting to perform a guest-only operation";


    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public BSResult handleShiroException(ShiroException e) {
        String eName = e.getClass().getSimpleName();
        log.error("shiro执行出错：{}", eName);
        return BSResult.failure(ExceptionEnum.AUTH_FAIL);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public Object  page401(HttpServletRequest reqest,
                            HttpServletResponse response, UnauthenticatedException e) throws IOException {
        String eMsg = e.getMessage();
        if(isAjax(reqest)) {
            BSResult bsResult;
            if (StringUtils.startsWithIgnoreCase(eMsg, GUEST_ONLY)) {
                bsResult =  BSResult.failure(ExceptionEnum.GUEST_ONLY);
            } else {
                bsResult =   BSResult.failure(ExceptionEnum.UNLOGIN);
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            //具体操作
            writer.write(JSONObject.toJSONString(bsResult));
            //
            writer.flush();
            writer.close();
            return null;
        }else{
            return "error/401";
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Object  page403(HttpServletRequest reqest,
                            HttpServletResponse response,UnauthorizedException e) throws IOException {
        if(isAjax(reqest)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            //具体操作
            writer.write(JSONObject.toJSONString(BSResult.failure(ExceptionEnum.NO_PERMISSION)));
            //
            writer.flush();
            writer.close();
            return null;
        }else{
            return "error/403";
        }
    }

    /**
     * 判断是否是ajax请求
     */
    public static boolean isAjax(HttpServletRequest httpRequest) {
        return (httpRequest.getHeader("X-Requested-With") != null
                && "XMLHttpRequest"
                .equals(httpRequest.getHeader("X-Requested-With").toString()));
    }

}

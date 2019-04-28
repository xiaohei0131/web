package com.business.common.handler;

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
    @ResponseBody
    public BSResult page401(UnauthenticatedException e) {
        String eMsg = e.getMessage();
        if (StringUtils.startsWithIgnoreCase(eMsg, GUEST_ONLY)) {
            return BSResult.failure(ExceptionEnum.GUEST_ONLY);
        } else {
            return BSResult.failure(ExceptionEnum.UNLOGIN);
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public BSResult page403() {
        return BSResult.failure(ExceptionEnum.NO_PERMISSION);
    }

}

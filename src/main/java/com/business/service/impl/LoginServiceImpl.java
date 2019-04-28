package com.business.service.impl;

import com.business.bean.SysUser;
import com.business.common.BSResult;
import com.business.common.exception.ExceptionEnum;
import com.business.common.utils.KeyGenerator;
import com.business.dao.UserDao;
import com.business.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginServiceImpl implements LoginService {
    private static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public BSResult login(String userName, String password) {
        if (userName == null || userName.isEmpty()) {
            return BSResult.failure(ExceptionEnum.USERNAME_EMPTY);
        }
        // 1、获取Subject实例对象
        Subject currentUser = SecurityUtils.getSubject();

        // 2、判断当前用户是否登录
        if (currentUser.isAuthenticated() == true) {
            return BSResult.success();
        }

        // 3、将用户名和密码封装到UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        // 4、认证
        try {
            currentUser.login(token);// 传到MyAuthorizingRealm类中的方法进行认证
            Session session = currentUser.getSession();
            session.setAttribute("userName", userName);
            return BSResult.success();
            //return "/index";
        } catch (UnknownAccountException e) {
            log.error("账号不存在");
            return BSResult.failure(ExceptionEnum.UNKNOWN_ACCOUNT);
        } catch (IncorrectCredentialsException e) {
            log.error("密码不正确");
            return BSResult.failure(ExceptionEnum.ERROR_PASSWORD);
        } catch (AuthenticationException e) {
            log.error("用户验证失败");
            return BSResult.failure(ExceptionEnum.AUTH_FAIL);
        }
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @Autowired
    UserDao userDao;

    @Override
    public BSResult register(SysUser sysUser) {
        sysUser.setSalt(KeyGenerator.getKey());
        String newPs = new SimpleHash("MD5", sysUser.getPassword(), ByteSource.Util.bytes(sysUser.getSalt()), 1024).toHex();
        sysUser.setCreateTime(new Date());
        sysUser.setPassword(newPs);
        sysUser.setState(SysUser.NORMAL);
        synchronized (this) {
            if (userDao.findByUserName(sysUser.getUsername()) == null) {
                int num = userDao.addUser(sysUser);
                if (num == 1) {
                    return BSResult.success();
                }
                return BSResult.failure();
            } else {
                return BSResult.failure(ExceptionEnum.ACCOUNT_USED);
            }
        }
    }
}

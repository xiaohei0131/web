package com.business.controller;

import com.business.bean.SysUser;
import com.business.common.BSResult;
import com.business.common.exception.BusinessException;
import com.business.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SSOController {

    @Autowired
    LoginService loginService;

    @GetMapping("/login.html")
    public String loginPage() {
        return "sys/login";
    }

    @PostMapping("/logout")
    @ResponseBody
    public void logout() {
        loginService.logout();
    }

    @PostMapping("/login")
    @ResponseBody
    public BSResult login(@RequestParam("username") String username,@RequestParam("password") String password) {
        return loginService.login(username, password);
    }

    @PostMapping("/register")
    @ResponseBody
    public BSResult register(@RequestParam("username") String username,
                             @RequestParam("nickName") String nickName,
                             @RequestParam("password") String password,
                             @RequestParam("email") String email,
                             @RequestParam("phone") String phone) {
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setNickName(nickName);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        return loginService.register(user);
    }
}

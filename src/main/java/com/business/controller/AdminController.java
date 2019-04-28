package com.business.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/index")
//    @RequiresAuthentication
    public String index() {
        return "sys/index";
    }

    @GetMapping("/auth")
//    @RequiresAuthentication
    @ResponseBody
    public String auth() {
        return "sys/index";
    }
}

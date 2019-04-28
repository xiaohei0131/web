package com.business.service;

import com.business.bean.SysUser;

public interface UserService {
    SysUser findByUserName(String userName);
}

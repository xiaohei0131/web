package com.business.service;

import com.business.bean.SysUser;
import com.business.common.BSResult;

public interface LoginService {
    BSResult login(String userName, String password);

    BSResult register(SysUser sysUser);

    void logout();
}

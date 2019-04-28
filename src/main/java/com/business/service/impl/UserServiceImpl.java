package com.business.service.impl;

import com.business.bean.SysUser;
import com.business.dao.UserDao;
import com.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public SysUser findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }
}

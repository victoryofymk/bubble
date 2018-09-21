package com.it.ymk.bubble.component.shiro.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.it.ymk.bubble.component.shiro.dao.UserInfoDao;
import com.it.ymk.bubble.component.shiro.entity.SysUser;
import com.it.ymk.bubble.component.shiro.service.UserInfoService;

/**
 * @author yanmingkun
 * @date 2018-09-20 13:30
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public SysUser findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
}

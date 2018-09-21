package com.it.ymk.bubble.component.shiro.service;

import com.it.ymk.bubble.component.shiro.entity.SysUser;

/**
 * @author yanmingkun
 * @date 2018-09-20 13:30
 */
public interface UserInfoService {

    /**通过username查找用户信息;*/
    public SysUser findByUsername(String username);
}

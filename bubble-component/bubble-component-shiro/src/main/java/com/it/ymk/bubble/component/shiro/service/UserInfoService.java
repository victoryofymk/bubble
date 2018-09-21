package com.it.ymk.bubble.component.shiro.service;

import java.util.List;

import com.it.ymk.bubble.component.shiro.entity.SysUser;

/**
 * @author yanmingkun
 * @date 2018-09-20 13:30
 */
public interface UserInfoService {

    /**通过username查找用户信息;*/
    public SysUser findByUsername(String username);

    public List<SysUser> getUserList();

    public SysUser findUserById(long id);

    public void save(SysUser user);

    public void edit(SysUser user);

    public void delete(long id);
}

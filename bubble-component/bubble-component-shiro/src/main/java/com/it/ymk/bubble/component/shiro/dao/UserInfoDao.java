package com.it.ymk.bubble.component.shiro.dao;

import org.springframework.data.repository.CrudRepository;

import com.it.ymk.bubble.component.shiro.entity.SysUser;

/**
 * @author yanmingkun
 * @date 2018-09-20 13:33
 */
public interface UserInfoDao extends CrudRepository<SysUser, Long> {
    /**通过username查找用户信息;*/
    public SysUser findByUsername(String username);
}

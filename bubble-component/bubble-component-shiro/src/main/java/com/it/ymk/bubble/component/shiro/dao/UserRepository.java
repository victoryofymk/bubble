package com.it.ymk.bubble.component.shiro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it.ymk.bubble.component.shiro.entity.SysUser;

/**
 * @author yanmingkun
 * @date 2018-09-21 17:56
 */
public interface UserRepository extends JpaRepository<SysUser, Long> {
    SysUser findById(long id);

    SysUser findByUsername(String username);
}

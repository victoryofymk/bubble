package com.it.ymk.bubble.component.shiro.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.it.ymk.bubble.component.shiro.dao.SysUserRepository;
import com.it.ymk.bubble.component.shiro.entity.SysUser;
import com.it.ymk.bubble.component.shiro.service.SysUserService;

/**
 * @author yanmingkun
 * @date 2018-09-20 13:30
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    private static Logger     logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserRepository sysUserRepository;

    @Override
    public SysUser findByUsername(String username) {
        logger.debug("SysUserServiceImpl.findByUsername()");
        return sysUserRepository.findByUsername(username);
    }

    @Override
    public List<SysUser> getUserList() {
        return sysUserRepository.findAll();
    }

    @Override
    public SysUser findUserById(long id) {
        return sysUserRepository.findById(id);
    }

    @Override
    public void save(SysUser user) {
        sysUserRepository.save(user);
    }

    @Override
    public void edit(SysUser user) {
        sysUserRepository.save(user);
    }

    @Override
    public void delete(long id) {
        sysUserRepository.deleteById(id);
    }
}

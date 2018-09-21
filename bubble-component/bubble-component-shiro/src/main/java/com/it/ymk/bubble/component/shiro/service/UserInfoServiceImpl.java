package com.it.ymk.bubble.component.shiro.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.it.ymk.bubble.component.shiro.dao.UserRepository;
import com.it.ymk.bubble.component.shiro.entity.SysUser;
import com.it.ymk.bubble.component.shiro.service.UserInfoService;

/**
 * @author yanmingkun
 * @date 2018-09-20 13:30
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserRepository userRepository;

    @Override
    public SysUser findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userRepository.findByUsername(username);
    }

    @Override
    public List<SysUser> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public SysUser findUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(SysUser user) {
        userRepository.save(user);
    }

    @Override
    public void edit(SysUser user) {
        userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}

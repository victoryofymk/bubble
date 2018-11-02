package com.it.ymk.bubble.component.shiro.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.ymk.bubble.component.shiro.entity.SysUser;
import com.it.ymk.bubble.component.shiro.service.SysUserService;

/**
 * @author yanmingkun
 * @date 2018-09-20 16:21
 */
@Controller
@RequestMapping("/userInfo")
public class SysUserController {

    private static Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Resource
    SysUserService        userService;

    @RequestMapping("/")
    public String index() {
        return "redirect:/list";
    }

    /**
     * 模版用户查询
     * @return
     */
    @RequestMapping("/thymeleaf/list")
    @RequiresPermissions("userInfo:view") //权限管理;
    //    @Cacheable(value = "user-key")
    public String userInfo(Model model) {
        logger.debug("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        List<SysUser> users = userService.getUserList();
        model.addAttribute("users", users);

        return "userInfoList";
    }

    /**
     * 用户添加
     * @return
     */
    @RequestMapping("/thymeleaf/userAdd")
    @RequiresPermissions("userInfo:add") //权限管理;
    public String userInfoAdd() {
        return "userInfoAdd";
    }

    /**
     * 用户添加跳转
     * @return
     */
    @RequestMapping("/thymeleaf/toAdd")
    public String toAdd() {
        return "userInfoAdd";
    }

    /**
     * 用户添加请求
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/thymeleaf/add")
    public String add(@RequestParam(value = "username") String username,
        @RequestParam(value = "password") String password) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        userService.save(sysUser);
        return "redirect:/userInfo/thymeleaf/list";
    }

    /**
     * 用户删除
     * @return
     */
    @RequestMapping("/thymeleaf/userDel")
    @RequiresPermissions("userInfo:del") //权限管理;
    public String userDel() {
        return "userInfoDel";
    }

    /**
     * 用户删除请求
     * @param id
     * @return
     */
    @RequestMapping("/thymeleaf/delete")
    public String delete(@RequestParam(value = "id") Long id) {
        userService.delete(id);
        return "redirect:/userInfo/thymeleaf/list";
    }

    /**
     * 用户编辑请求
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/thymeleaf/toEdit")
    public String toEdit(Model model, @RequestParam(value = "id") Long id) {
        SysUser user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "userInfoEdit";
    }

    /**
     * 用户编辑
     * @return
     */
    @RequestMapping("/thymeleaf/edit")
    public String edit(@RequestParam(value = "uid") Long uid, @RequestParam(value = "username") String username,
        @RequestParam(value = "password") String password) {
        SysUser user = new SysUser();
        user.setUid(uid);
        user.setUsername(username);
        user.setPassword(password);
        userService.edit(user);
        return "redirect:/userInfo/thymeleaf/list";
    }

    /**
     * 普通查询
     * @param model
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("userInfo:view") //权限管理;
    public String list(Model model) {
        List<SysUser> users = userService.getUserList();
        model.addAttribute("users", users);
        return "userInfoList";
    }

}

package com.it.ymk.bubble.component.shiro.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.ymk.bubble.component.shiro.entity.SysUser;
import com.it.ymk.bubble.component.shiro.service.UserInfoService;

/**
 * @author yanmingkun
 * @date 2018-09-20 16:21
 */
@Controller
@RequestMapping("/userInfo")
public class SysUserController {
    @Resource
    UserInfoService userService;

    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/userList")
    @RequiresPermissions("userInfo:view") //权限管理;
    @Cacheable(value = "user-key")
    public String userInfo() {
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return "userInfoList";
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add") //权限管理;
    public String userInfoAdd() {
        return "userInfoAdd";
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del") //权限管理;
    public String userDel() {
        return "userInfoDel";
    }

    @RequestMapping("/")
    public String index() {
        return "redirect:/list";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        List<SysUser> users = userService.getUserList();
        model.addAttribute("users", users);
        return "userInfoList";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "userInfoAdd";
    }

    @RequestMapping("/add")
    public String add(@RequestBody SysUser user) {
        userService.save(user);
        return "redirect:/list";
    }

    @RequestMapping("/toEdit")
    public String toEdit(Model model, @RequestParam(value = "id") Long id) {
        SysUser user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "userInfoEdit";
    }

    @RequestMapping("/edit")
    public String edit(@RequestBody SysUser user) {
        userService.edit(user);
        return "redirect:/list";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam(value = "id") Long id) {
        userService.delete(id);
        return "redirect:/list";
    }

}

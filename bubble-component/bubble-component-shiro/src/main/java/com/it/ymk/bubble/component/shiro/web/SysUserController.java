package com.it.ymk.bubble.component.shiro.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yanmingkun
 * @date 2018-09-20 16:21
 */
@Controller
@RequestMapping("/userInfo")
public class SysUserController {
    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/userList")
    @RequiresPermissions("userInfo:view") //权限管理;
    public String userInfo() {
        return "userInfo";
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
}

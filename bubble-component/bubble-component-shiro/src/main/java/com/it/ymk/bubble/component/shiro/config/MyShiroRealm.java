package com.it.ymk.bubble.component.shiro.config;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.it.ymk.bubble.component.shiro.entity.SysPermission;
import com.it.ymk.bubble.component.shiro.entity.SysRole;
import com.it.ymk.bubble.component.shiro.entity.SysUser;
import com.it.ymk.bubble.component.shiro.service.SysUserService;

/**
 * @author yanmingkun
 * @date 2018-09-20 13:29
 */
public class MyShiroRealm extends AuthorizingRealm {

    private static Logger  logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Resource
    private SysUserService sysUserService;

    /**
     * 获取授权信息，权限认证
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.debug("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();
        for (SysRole role : sysUser.getRoleList()) {
            authorizationInfo.addRole(role.getRole());
            for (SysPermission p : role.getPermissions()) {
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }

    /**
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
        throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();
        logger.info("对用户[{}]进行登录验证..验证开始", username);
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser sysUser = sysUserService.findByUsername(username);
        if (sysUser == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(),
            ByteSource.Util.bytes(sysUser.getCredentialsSalt()), getName());
    }
}

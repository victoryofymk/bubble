package com.it.ymk.bubble.core.mybatis.annotation;

import java.util.List;

import org.apache.ibatis.annotations.*;

/**
 * 基于注解的mapper
 * @author yanmingkun
 * @date 2018-09-14 16:12
 */
@Mapper
public interface UserMapper {

    /**
     * 方式1：使用注解编写SQL。
     */
    @Select("select * from t_user")
    @Results({
               @Result(property = "userId", column = "USER_ID"),
               @Result(property = "username", column = "USERNAME"),
               @Result(property = "password", column = "PASSWORD"),
               @Result(property = "mobileNum", column = "PHONE_NUM")
    })
    List<User> list();

    /**
     * 方式2：使用注解指定某个工具类的方法来动态编写SQL.
     */
    @SelectProvider(type = UserSqlProvider.class, method = "listByUsername")
    List<User> listByUsername(String username);

    /**
     * 延伸：上述两种方式都可以附加@Results注解来指定结果集的映射关系.
     *
     * PS：如果符合下划线转驼峰的匹配项可以直接省略不写。
     */
    @Results({
               @Result(property = "userId", column = "USER_ID"),
               @Result(property = "username", column = "USERNAME"),
               @Result(property = "password", column = "PASSWORD"),
               @Result(property = "mobileNum", column = "PHONE_NUM")
    })
    @Select("select * from t_user")
    List<User> listSample();

    /**
     * 延伸：无论什么方式,如果涉及多个参数,则必须加上@Param注解,否则无法使用EL表达式获取参数。
     */
    @Select("select * from t_user where username like #{username} and password like #{password}")
    User get(@Param("username") String username, @Param("password") String password);

    @SelectProvider(type = UserSqlProvider.class, method = "getBadUser")
    User getBadUser(@Param("username") String username, @Param("password") String password);

}

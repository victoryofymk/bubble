package com.it.ymk.bubble.core.mybatis.generic;

import java.util.List;

/**
 * 通用mybatis集成，去掉XML配置，使用方法：
 * 
 * 1.在Mapper接口继承通用Mapper并指定泛型，例：public interface UserMapper extends Mapper<User>
 * 2.在Service实现类继承通用Service，例：
 *   @Service
 *   public class UserServiceImpl extends BaseServiceImpl<User> implements UserService
 * 3.最后在Controller 提供RestFul API，如下
 *     @Autowired
 *     UserService userService;
 *
 *     @RequestMapping("list")
 *     public List<User> list(User user) {
 *         return userService.list(user);
 *     }
 *  4.entity使用@Table(name = "T_USER")，@Id，@Column(name = "USER_ID")，@Transient（跟数据库无关的字段使用@Transient标记或移至VO类）
 *  5.mybatis:
 *      configuration:
 *      #配置项：开启下划线到驼峰的自动转换. 作用：将数据库字段根据驼峰规则自动注入到对象属性。
 *      map-underscore-to-camel-case: true
 * @author yanmingkun
 * @date 2018-09-14 15:18
 */
public interface BaseService<T> {
    List<T> list(T entity);

    T get(T entity);

    int update(T entity);

    int save(T entity);

    int delete(T entity);
}

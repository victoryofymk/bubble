# 本文简介
  * [本文简介](#本文简介)
      * [项目介绍](#项目介绍)
      * [认识springboot](#认识springboot)
         * [springboot 简介](#springboot-简介)
         * [说明文档](#说明文档)
         * [环境搭建](#环境搭建)
         * [基础配置](#基础配置)
            * [properties和yml](#properties和yml)
            * [编译工具](#编译工具)
               * [多版本使用profile](#多版本使用profile)
               * [本地库依赖](#本地库依赖)
               * [指定编译版本](#指定编译版本)
               * [指定打包译版本](#指定打包译版本)
            * [常用组件](#常用组件)
      * [项目框架](#项目框架)
         * [前端](#前端)
            * [模块](#模块)
            * [api](#api)
            * [grid](#grid)
               * [插件](#插件)
               * [扩展](#扩展)
                  * [跨页勾选](#跨页勾选)
            * [资源访问](#资源访问)
            * [WebJars统一管理静态资源](#webjars统一管理静态资源)
         * [后端](#后端)
            * [集成Mybatis](#集成mybatis)
               * [配置druid数据库连接池](#配置druid数据库连接池)
               * [配置分页插件](#配置分页插件)
               * [mybatis扫描](#mybatis扫描)
            * [CXF 整合restful风格服务](#cxf-整合restful风格服务)
            * [集成Swagger2](#集成swagger2)
      * [部署](#部署)
         * [打包](#打包)
         * [制作docker镜像](#制作docker镜像)
         * [本地启动](#本地启动)
         * [docker启动](#docker启动)
      * [其他](#其他)
         * [代码优化](#代码优化)
            * [java](#java)
               * [string拼接占位符](#string拼接占位符)
               * [使用logback占位符{}](#使用logback占位符)
## 项目介绍
   基于springboot实现的后端框架，集成 mybatis、quartzs、swagger-ui等通用组件，精简配置文件，习惯由于配置。
## 认识springboot
### springboot 简介
### 说明文档
### 环境搭建
### 基础配置
#### properties和yml
yml相对于properties更加精简而且很多官方给出的Demo都是yml的配置形式，在这里我们采用yml，相对于properties形式主要有以下两点不同
>1. 对于键的描述由原有的 "." 分割变成了树的形状
>2. 对于所有的键的后面一个要跟一个空格，不然启动项目会报配置解析错误
    
```
# properties式语法描述
spring.datasource.name = mysql
spring.datasource.url = jdbc:mysql://localhost:3306/root?characterEncoding=utf-8
spring.datasource.username = root
spring.datasource.password = root
# yml式语法描述
spring:
    datasource:
        name: mysql
        url: jdbc:mysql://localhost:3306/root?characterEncoding=utf-8
        username: root
        password: root
```
#### 编译工具
maven
##### 多版本使用profile
##### 本地库依赖
    mvn install
##### 指定编译版本
    mvn install  -Pdev
    IDE可以启动任何指定版本，需要clean后才会生效，IDEA需要配置启动前自定义编译MAVEN需要使用install和指定profile
##### 指定打包译版本
    mvn clean package -Pdev
#### 常用组件
基础依赖配置pom.xml文件中的常见依赖，例如Web、Mybatis、test以及Mysql
     
 ```
 <!-- spring web mvc -->
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-web</artifactId>
 </dependency>
 <!-- mybatis -->
 <dependency>
     <groupId>org.mybatis.spring.boot</groupId>
     <artifactId>mybatis-spring-boot-starter</artifactId>
     <version>1.3.1</version>
 </dependency>
 <!-- mysql -->
 <dependency>
     <groupId>mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
     <scope>runtime</scope>
 </dependency>
 <!-- test -->
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-test</artifactId>
     <scope>test</scope>
 </dependency>
 ```
     
 增加druid数据源、fastjson、pagehelper分页插件，整合swagger2文档自动化构建框架
     
 ```
<!-- 分页插件 -->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.2.3</version>
</dependency>
<!-- alibaba的druid数据库连接池 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>
<!-- alibaba的json格式化对象 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.31</version>
</dependency>
<!-- 自动生成API文档 -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.5.0</version>
</dependency>
 ```
     
## 项目框架

### 前端
#### 模块
requirejs
#### api
#### grid
##### 插件
easyuigrid
##### 扩展
###### 跨页勾选
#### 资源访问
 springboot 资源文件说明
    
 ```
/META-INF/resources/
/resources/
/static/
/public/
 ```
    
 spring boot默认加载文件的路径是
    
 ```
 /META-INF/resources/
 /resources/
 /static/
 /public/
 ```
    
    META_INF/resources 下面的静态资源文件可以覆盖引用的 jar 保重的资源文件
#### WebJars统一管理静态资源

##### 引入相关依赖
```
<!--Webjars版本定位工具（前端）-->
<dependency>
 <groupId>org.webjars</groupId>
 <artifactId>webjars-locator-core</artifactId>
</dependency>
<!--Jquery组件（前端）-->
<dependency>
 <groupId>org.webjars</groupId>
 <artifactId>jquery</artifactId>
 <version>3.3.1</version>
 </dependency>
```

##### 访问静态资源
在浏览器访问静态资源：

快速访问：http://localhost:8080/webjars/jquery/jquery.js （推荐）

快速访问：http://localhost:8080/webjars/jquery/3.3.1/jquery.js

webjars-locator-core 解决访问WebJars静态资源时必须携带版本号的繁琐问题

##### 静态资源版本化

1.简洁做法

新建一个SpringBoot项目，手工创建目录 META-INF/resources/ ，将静态资源完整复制进去，然后发布公司Maven私服即可。

2.标准的Webjars格式

1、新建SpringBoot工程 然后在srcmain esources 新建目录 META-INF.resources.webjars.metronic.4.1.9 重点来了 这里4.1.9 必须跟POM文件的<version>4.1.9</version>保持一致。
2、修改POM文件 填写项目信息和公司私服地址。

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
 <modelVersion>4.0.0</modelVersion>
 <!--项目信息-->
 <groupId>org.webjars</groupId>
 <artifactId>metronic</artifactId>
 <version>4.1.9</version>
 <packaging>jar</packaging>
 <name>metronic</name>
 <description>metronic</description>
 
 <!--维护信息-->
 <developers>
     <developer>
     <name>socks</name>
     <email>https://github.com/yizhiwazi</email>
     </developer>
 </developers>
 <!--发布地址-->
 <distributionManagement>
     <repository>
         <id>xx-repo</id>
         <!--这里替换成公司私服地址-->
         <url>http://127.0.0.1:8088/nexus/content/repositories/thirdparty/</url>
     </repository>
     <snapshotRepository>
         <id>xx-plugin-repo</id>
         <!--这里替换成公司私服地址-->
         <url>http://127.0.0.1:8088/nexus/content/repositories/thirdparty/</url>
     </snapshotRepository>
 </distributionManagement>
</project>
```
3、在本地MAVEN的配置文件指定公司私服的账号密码。
```
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
 <localRepository>D:devmvnrepository</localRepository>
 <mirrors>
     <!-- 阿里云仓库 -->
     <mirror>
     <id>aliyun</id>
     <mirrorOf>central</mirrorOf>
     <name>aliyun-all</name>
     <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
     </mirror>
 
     <!-- 中央仓库1 -->
     <mirror>
     <id>repo1</id>
     <mirrorOf>central</mirrorOf>
     <name>Human Readable Name for this Mirror.</name>
     <url>http://repo1.maven.org/maven2/</url>
     </mirror>
 
     <!-- 中央仓库2 -->
     <mirror>
     <id>repo2</id>
     <mirrorOf>central</mirrorOf>
     <name>Human Readable Name for this Mirror.</name>
     <url>http://repo2.maven.org/maven2/</url>
     </mirror>
 </mirrors> 
 
 <!-- 暂时在发布仓库到213的时候用到-->
 <servers>
     <!-- 仓库地址账号 -->
     <server>
         <id>xx-repo</id>
         <username>admin</username>
         <password>123456</password>
     </server>
     <!-- 插件地址账号 -->
     <server>
         <id>xx-plugin-repo</id>
         <username>admin</username>
         <password>123456</password>
     </server>
 </servers>
</settings>

```
4、打开IDEA->Maven->Deploy 将项目到公司私服，大功告成。

### 后端
#### 就成spring-boot-starter-data-jpa
spring-boot-starter-data-jpa，示例模块：bubble-component-shiro

添加依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

添加配置,ddl-auto设置为cteate表示每次重新初始化数据库


```
#ddl-auto 四个值的解释
#create： 每次加载hibernate时都会删除上一次的生成的表，然后根据你的model类再重新来生成新表，哪怕两次没有任何改变也要这样执行，这就是导致数据库表数据丢失的一个重要原因。
#create-drop ：每次加载hibernate时根据model类生成表，但是sessionFactory一关闭,表就自动删除。
#update：最常用的属性，第一次加载hibernate时根据model类会自动建立起表的结构（前提是先建立好数据库），以后加载hibernate时根据 model类自动更新表结构，即使表结构改变了但表中的行仍然存在不会删除以前的行。要注意的是当部署到服务器后，表结构是不会被马上建立起来的，是要等 应用第一次运行起来后才会。
#validate ：每次加载hibernate时，验证创建数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值。 5、 none : 什么都不做。
spring.jpa.database=mysql
spring.jpa.show_sql=true
spring.jpa.hibernate.ddl-auto=create
spring.jpa.database-platform=com.it.ymk.bubble.config.MySQL5DialectUTF8
```

在src/main/resources下面创建import.sql，每次启动会执行此脚本

```
INSERT INTO `sys_user` (`uid`,`username`,`name`,`password`,`salt`,`state`) VALUES ('1', 'admin', '管理员', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f', 0);
INSERT INTO `sys_permission` (`id`,`available`,`name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`) VALUES (1,0,'用户管理',0,'0/','userInfo:view','menu','userInfo/userList');
INSERT INTO `sys_permission` (`id`,`available`,`name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`) VALUES (2,0,'用户添加',1,'0/1','userInfo:add','button','userInfo/userAdd');
INSERT INTO `sys_permission` (`id`,`available`,`name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`) VALUES (3,0,'用户删除',1,'0/1','userInfo:del','button','userInfo/userDel');
INSERT INTO `sys_role` (`id`,`available`,`description`,`role`) VALUES (1,0,'管理员','admin');
INSERT INTO `sys_role` (`id`,`available`,`description`,`role`) VALUES (2,0,'VIP会员','vip');
INSERT INTO `sys_role` (`id`,`available`,`description`,`role`) VALUES (3,1,'test','test');
INSERT INTO `sys_role_permission` VALUES ('1', '1');
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (1,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (2,1);
INSERT INTO `sys_role_permission` (`permission_id`,`role_id`) VALUES (3,2);
INSERT INTO `sys_user_role` (`role_id`,`uid`) VALUES (1,1);
```

#### 集成Mybatis
推荐需要较多业务逻辑的模块使用此组件，实现有基于xml和无xml方式

本配置主要包括了druid数据库连接池、pagehelper分页插件、mybatis-generator插件以及mapper、pojo扫描配置
##### 配置druid数据库连接池
添加如下配置

```
spring:
    datasource:
        # 如果存在多个数据源，监控的时候可以通过名字来区分开来
        name: mysql
        # 连接数据库的url
        url: jdbc:mysql://localhost:3306/db?characterEncoding=utf-8
        # 连接数据库的账号
        username: root
        #  连接数据库的密码
        password: 123
        # 使用druid数据源
        type: com.alibaba.druid.pool.DruidDataSource
        # 扩展插件
        # 监控统计用的filter:stat 日志用的filter:log4j 防御sql注入的filter:wall
        filters: stat
```

##### 配置分页插件

```
#pagehelper 分页插件
pagehelper:
    # 数据库的方言
    helperDialect: mysql
    # 启用合理化，如果pageNum < 1会查询第一页，如果pageNum > pages会查询最后一页
    reasonable: true
```

##### mybatis扫描

>1.在application.yml配置mapper.xml以及pojo的包地址

```
#mybatis 配置
mybatis.type-aliases-package=com.it.ymk.bubble.core.entity,com.it.ymk.bubble.component.schedule.entity
mybatis.mapper-locations=classpath*:com/it/ymk/bubble/component/schedule/mapper/mysql/*Mapper.xml
```
    
>2.在启动类开启Mapper扫描注解

```
@Controller
@SpringBootApplication
@MapperScan("com.it.ymk.bubble.**.dao")
@ImportResource(locations = { "classpath:cxf.xml" })
public class Application {

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello Spring Boot World!";
    }

    /**
     * 异常访问
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/errorIndex")
    @ResponseBody
    public String testError() throws Exception {
        ErrorUtil.randomException();
        return "Hello Spring Boot World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
#### CXF 整合restful风格服务
rest服务

```
@Path("/restService")
public class RestServiceDemoImpl {
    /**
     *获取资源的列表，用复数的形式，GET方法
     **/
    @Path("/getCarList")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HashMap> getCarList() throws Exception {
        //方法名可以根据业务等取名,查询是的参数放入对象中，但是在开发的时候遇到的问题是获取列表的种类多种多样，
        //获取的时候，需要在service中进行复杂判断
        ArrayListWrapper w = new ArrayListWrapper();
        List<HashMap> resultList = new ArrayList();
        HashMap hashMap = new HashMap();
        hashMap.put("name", "yuhui");
        resultList.add(hashMap);
        w.myArray = resultList;
        return resultList;
    }

```

在启动类添加配置：
```
@ImportResource(locations = { "classpath:cxf.xml" })
```

文件配置：

```
#xml
<bean id="restService" class="com.it.ymk.bubble.web.service.rest.RestServiceDemoImpl">
    </bean>

    <jaxrs:server id="restContainer" address="/rs">
        <!--编码格式-->
        <jaxrs:languageMappings>

        </jaxrs:languageMappings>
        <!--输入拦截器设置-->
        <jaxrs:inInterceptors>

        </jaxrs:inInterceptors>

        <!--输出拦截器设置-->
        <jaxrs:outInterceptors>

        </jaxrs:outInterceptors>

        <jaxrs:serviceBeans>
            <ref bean="restService"/>
        </jaxrs:serviceBeans>

        <jaxrs:providers>
            <bean class="com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider"/>
        </jaxrs:providers>

        <!--支持的协议-->
        <jaxrs:extensionMappings>
            <entry key="json" value="application/json"/>
            <entry key="xml" value="application/xml"/>
        </jaxrs:extensionMappings>
    </jaxrs:server>
```

代码配置

```
#java
@Configuration
public class CxfConfig {
    /**
     * webservice
     *
     * @return
     */
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    /**
     * 注入webService
     *
     * @return
     */
    @Bean
    @Qualifier("WebserviceImpl")
    public Endpoint endpoint(WebserviceImpl WebserviceImpl) {
        EndpointImpl endpoint = new EndpointImpl(springBus(), WebserviceImpl);
        // 暴露webService api
        endpoint.publish("/ws");
        return endpoint;
    }

    /**
     *cxf实现restful风格
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean dispatcherCxfRestServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/cxf/*");
    }
}

```
#### 集成Swagger2 
>1. 建立SwaggerConfig文件

```
@Configuration //标记配置类
@EnableSwagger2 //开启在线接口文档
@Profile({ "dev", "test" })
public class Swagger2Config {
    /**
     * 接口版本号
     */
    private final String VERSION = "版本：1.0";
    /**
     * 接口大标题
     */
    private final String TITLE = "标题：XX_接口文档";
    /**
     * 具体的描述
     */
    private final String DESCRIPTION = "描述：接口说明文档..";
    /**
     * 服务说明url
     */
    private final String TERMS_OF_SERVICE_URL = "http://www.xxx.com";
    /**
     * licence
     */
    private final String LICENSE = "MIT";
    /**
     * licnce url
     */
    private final String LICENSE_URL = "https://mit-license.org/";
    /**
     * 接口作者联系方式
     */
    private final Contact CONTACT = new Contact("starlord", "https://github.com/victoryofymk", "starlord.yan@gmail.com");
    /**
     * 添加摘要信息(Docket)
     */
    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .termsOfServiceUrl(TERMS_OF_SERVICE_URL)
                .license(LICENSE)
                .licenseUrl(LICENSE_URL)
                .contact(CONTACT)
                .version(VERSION)
                .build())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.it.ymk"))
            .paths(PathSelectors.any())
            .build();
    }

}
```

>2. 常用注解示例

```
@Api("Quartz定时任务管理")
@RestController
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private IJobAndTriggerService iJobAndTriggerService;

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler             scheduler;

    /**
     * 新增一个job
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @ApiOperation(" 新增一个job")
    @ApiImplicitParams({ @ApiImplicitParam(name = "jobClassName", value = "任务class名称", dataType = "String"),
                         @ApiImplicitParam(name = "jobGroupName", value = "任务分组名称", dataType = "String"),
                         @ApiImplicitParam(name = "cronExpression", value = "任务时间表达式  ", dataType = "String") })
    @RequestMapping(value = "/addSingleJob")
    public void addSingleJob(@RequestParam(value = "jobClassName") String jobClassName,
        @RequestParam(value = "jobGroupName") String jobGroupName,
        @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        addJob(jobClassName, jobGroupName, cronExpression);
    }
```
>3. 生成json形式的文档

控制台会打印级别为INFO的日志，表明可通过访问应用的v2/api-docs接口得到文档api的json格式数据，可在浏览器输入指定地址验证集成是否成功
```
 Mapped "{[/v2/api-docs],methods=[GET],produces=[application/json || application/hal+json]}" 
 http://localhost:8080/v2/api-docs
```
>4. 集成官方的页面

```
<!--添加Swagger-UI依赖 -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.7.0</version>
</dependency>
```
访问页面

```
http://localhost:8090/bubble/swagger-ui.html
```
实现中文覆盖官方页面，在/src/main/resources/META-INF/resources/ 路径下新增 swagger-ui.html，并添加中文

```
<!--国际化操作：选择中文版 -->
    <script src='webjars/springfox-swagger-ui/lang/translator.js' type='text/javascript'></script>
    <script src='webjars/springfox-swagger-ui/lang/zh-cn.js' type='text/javascript'></script>
```
zh-cn.js 在src/main/resources/META-INF/resources/webjars/springfox-swagger-ui/lang/zh-cn.js，内容：

```
'use strict';

/* jshint quotmark: double */
window.SwaggerTranslator.learn({
    "Warning: Deprecated": "警告：已过时",
    "Implementation Notes": "实现备注",
    "Response Class": "响应类",
    "Status": "状态",
    "Parameters": "参数",
    "Parameter": "参数",
    "Value": "值",
    "Description": "描述",
    "Parameter Type": "参数类型",
    "Data Type": "数据类型",
    "Response Messages": "响应消息",
    "HTTP Status Code": "HTTP状态码",
    "Reason": "原因",
    "Response Model": "响应模型",
    "Request URL": "请求URL",
    "Response Body": "响应体",
    "Response Code": "响应码",
    "Response Headers": "响应头",
    "Hide Response": "隐藏响应",
    "Headers": "头",
    "Try it out!": "试一下！",
    "Show/Hide": "显示/隐藏",
    "List Operations": "显示操作",
    "Expand Operations": "展开操作",
    "Raw": "原始",
    "can't parse JSON.  Raw result": "无法解析JSON. 原始结果",
    "Example Value": "示例",
    "Click to set as parameter value": "点击设置参数",
    "Model Schema": "模型架构",
    "Model": "模型",
    "apply": "应用",
    "Username": "用户名",
    "Password": "密码",
    "Terms of service": "服务条款",
    "Created by": "创建者",
    "See more at": "查看更多：",
    "Contact the developer": "联系开发者",
    "api version": "api版本",
    "Response Content Type": "响应Content Type",
    "Parameter content type:": "参数类型:",
    "fetching resource": "正在获取资源",
    "fetching resource list": "正在获取资源列表",
    "Explore": "浏览",
    "Show Swagger Petstore Example Apis": "显示 Swagger Petstore 示例 Apis",
    "Can't read from server.  It may not have the appropriate access-control-origin settings.": "无法从服务器读取。可能没有正确设置access-control-origin。",
    "Please specify the protocol for": "请指定协议：",
    "Can't read swagger JSON from": "无法读取swagger JSON于",
    "Finished Loading Resource Information. Rendering Swagger UI": "已加载资源信息。正在渲染Swagger UI",
    "Unable to read api": "无法读取api",
    "from path": "从路径",
    "server returned": "服务器返回"
});
```
>5. 第三方页面增强-Swagger增强UI实现

https://github.com/xiaoymin/Swagger-Bootstrap-UI

说明：https://github.com/xiaoymin/Swagger-Bootstrap-UI/blob/master/README_zh.md

引入：
```
<dependency>
  <groupId>com.github.xiaoymin</groupId>
  <artifactId>swagger-bootstrap-ui</artifactId>
  <version>${lastVersion}</version>
</dependency>
```

访问：http://${host}:${port}/doc.html


## 部署
### 本地运行

配置中可以修改启动环境
```
spring.profiles.active=dev
```
本地运行在IDE直接运行com.it.ymk.bubble.Application

### 多环境打包

默认打包dev环境，-P指定环境

```
#指定打包版本
mvn package -Pdev
```

### jar包启动

```
java -jar XXX.jar
```

### 制作docker镜像
简洁版
```
#简洁版
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```
进阶版
```
#包含时区设置
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ${JAR_FILE} app.jar
# timezone
ARG TIME_ZONE=Asia/Shanghai

RUN apk add -U tzdata \
    && cp  /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```
### docker启动
基础依赖：docker、maven

编译打包镜像，执行 docker-maven-plugin
```
mvn package docker:build
```
或者，执行 dockerfile-maven-plugin，速度较快
```
mvn package docker:build
```
docker 查看
```
docker images
```

启动
```
docker run -p 8090:8090 -t bubble-springboot/bubble-springboot-web
```    
    
  
## 其他
### 代码优化


#### java  

##### 占位符


```
#string拼接占位符  
System.out.printf("该域名%s被访问了%s次.", domain , iVisit);
System.out.println(MessageFormat.format("该域名{0}被访问了 {1} 次.", domain , iVisit));
``` 


```
#使用logback占位符{} 
if(logger.isDebugEnabled()) { 
  logger.debug("Entry number: " + i + " is " + String.valueOf(entry[i]));
}
``` 



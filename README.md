# 本文简介
  - 项目介绍
  - 认识springboot
  - 项目框架
  - 部署启动方式
  - 其他
## 项目介绍
   springboot 集成 mybatis、quartzs、swagger-ui等通用组件，精简配置文件，习惯由于配置。
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


 
### 后端

#### 集成Mybatis
配置主要包括了druid数据库连接池、pagehelper分页插件、mybatis-generator插件以及mapper、pojo扫描配置
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

## 部署
### 打包
    #指定打包版本
    mvn package -Pdev
### 制作docker镜像
简洁版

    #简洁版
    FROM openjdk:8-jdk-alpine
    VOLUME /tmp
    ADD ${JAR_FILE} app.jar
    ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
    
进阶版
    
    #包含时区设置
    FROM openjdk:8-jdk-alpine
    VOLUME /tmp
    ADD ${JAR_FILE} app.jar
    # timezone
    ARG TIME_ZONE=Asia/Shanghai
    
    RUN apk add -U tzdata \
        && cp  /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime
    
    ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
### 本地启动
执行  
### docker启动
    基础依赖：
    docker、maven
    
    #编译打包镜像，执行 docker-maven-plugin
    mvn package docker:build
    #或者，执行 dockerfile-maven-plugin，速度较快
    mvn package docker:build 
    #docker 查看
    docker images
    #启动
    docker run -p 8090:8090 -t bubble-springboot/bubble-springboot-web
    
    
  
## 其他
### 代码优化


#### java  

##### string拼接占位符   
 System.out.printf("该域名%s被访问了%s次.", domain , iVisit);
 System.out.println(MessageFormat.format("该域名{0}被访问了 {1} 次.", domain , iVisit));
 
 工具：  

##### 使用logback占位符{}

if(logger.isDebugEnabled()) { 
  logger.debug("Entry number: " + i + " is " + String.valueOf(entry[i]));
}



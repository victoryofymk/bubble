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
### 最佳实践
#### properties和.yml
    yml相对于properties更加精简而且很多官方给出的Demo都是yml的配置形式，在这里我们采用yml的形式代替properties，相对于properties形式主要有以下两点不同
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
## 项目框架说明
### 工具 maven
#### 多版本使用profile
    
    <profiles>
            <!--开发环境-->
            <profile>
                <id>dev</id>
                <properties>
                    <profiles.activation>dev</profiles.activation>
                </properties>
                <activation>
                    <activeByDefault>true</activeByDefault>
                </activation>
                <!--<build>
                    <filters>
                        <filter>src/main/resources/dev/jdbc.properties</filter>
                    </filters>
                </build>-->
            </profile>
    
            <profile>
                <id>test</id>
                <properties>
                    <profiles.activation>test</profiles.activation>
                </properties>
                <!--<build>-->
                    <!--<filters>-->
                        <!--<filter>src/main/resources/test/jdbc.properties</filter>-->
                    <!--</filters>-->
                <!--</build>-->
            </profile>
    
            <!--生产环境-->
            <profile>
                <id>product</id>
                <properties>
                    <profiles.activation>pro</profiles.activation>
                </properties>
                <!--<build>
                    <filters>
                        <filter>src/main/resources/pro/jdbc.properties</filter>
                    </filters>
                </build>-->
            </profile>
        </profiles>

属性文件占位符

     <!-- ==========属性文件位置 ==========-->
        <!-- 取${profiles.activation:dev}表示取${profiles.activation}的值，若没有则指定dev -->
        <bean id="propertyConfig"
              class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
            <property name="locations">
                <list>
                    <value>classpath:${profiles.activation:dev}/jdbc.properties</value>
                </list>
            </property>
        </bean>

使用resource 指定资源文件

    <resources>
                <resource>
                    <directory>src/main/resources</directory>
    
                    <!-- **/*.properties 是指包括根目录或子目录所有properties类型的文件 -->
                    <includes>
                        <include>**/jdbc.properties</include>
                        <include>**/*.xml</include>
                    </includes>
    
                    <!-- 排除dev、test目录下的文件 -->
                    <excludes>
                        <exclude>dev/*</exclude>
                        <exclude>pro/*</exclude>
                        <exclude>test/*</exclude>
                    </excludes>
                    <!--<filtering>true</filtering>-->
                </resource>
    
                <resource>
                    <directory>src/main/resources</directory>
                    <!-- 包含，若没有指定则默认为 activeByDefault 标签定义的profile -->
                    <includes>
                        <include>${profiles.activation}/*</include>
                    </includes>
                </resource>
            </resources>
    
替换web.xml 展位符
    
    <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-war-plugin</artifactId>
                    <configuration>
                        <!--指定打包时名称-->
                        <warName>${name}_${profiles.activation}_${parent.version}</warName>
                        <!-- 激活spring profile -->
                        <webResources>
                            <resource>
                                <filtering>true</filtering>
                                <directory>src/main/webapp</directory>
                                <includes>
                                    <include>**/web.xml</include>
                                </includes>
                            </resource>
                        </webResources>
                        <warSourceDirectory>src/main/webapp</warSourceDirectory>
                        <webXml>src/main/webapp/WEB-INF/web.xml</webXml>
                    </configuration>
                </plugin>
#### 指定编译版本

    mvn install  -Pdev
    
    IDE可以启动任何指定版本，需要clean后才会生效，IDEA需要配置启动前自定义编译MAVEN需要使用install和指定profile
   
#### 指定打包译版本

    mvn clean package -Pdev
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

#### SSM（Spring SpringMVC Mybatis）

#### CXF 整合restful风格服务

web.xml配置：

    <!-- 配置CXF框架的核心Servlet，整合restful发布服务-->
    <servlet>
        <servlet-name>CXFServlet</servlet-name>
        <servlet-class>org.apache.cxf.transport.servlet.CXFServlet</servlet-class>
        <load-on-startup>2</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>CXFServlet</servlet-name>
        <url-pattern>/services/*</url-pattern>
    </servlet-mapping>

spring核心配置：

    <!--cxf-->
        <import resource="spring-cxf.xml"/>

    
服务配置：

    <jaxrs:server id="webService" address="/rest">
        <!--输入拦截器设置-->
        <jaxrs:inInterceptors>

        </jaxrs:inInterceptors>

        <!--输出拦截器设置-->
        <jaxrs:outInterceptors>

        </jaxrs:outInterceptors>

        <jaxrs:serviceBeans>
            <ref bean="restServiceDemoImpl"/>
        </jaxrs:serviceBeans>

        <!--支持的协议-->
        <jaxrs:extensionMappings>
            <entry key="json" value="application/json"/>
            <entry key="xml" value="application/xml"/>
        </jaxrs:extensionMappings>

        <!--编码格式-->
        <jaxrs:languageMappings>

        </jaxrs:languageMappings>

        <jaxrs:providers>
            <bean class="com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider"/>
        </jaxrs:providers>
    </jaxrs:server>    
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

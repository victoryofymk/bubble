package com.it.ymk.bubble.common.mybatis.interceptor;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  模糊查询拦截器，过滤通配符
 * 使用方法：
 * <bean id="searchInterceptor" class="com.***.springframework.web.interceptor.com.it.ymk.bubble.common.mybatis.interceptor.SearchInterceptor" >
 * </bean>
 * <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
 * <property name="plugins">
 * <list>
 * <ref bean="myInterceptor" />
 * <ref bean="searchInterceptor" />
 * </list>
 * </property>
 * </bean>
 *
 * @author Yanmingkun
 * @version $v:1.0, $time:2017-04-17, $id:com.it.ymk.bubble.common.mybatis.interceptor.SearchInterceptor.java, Exp $
 */
@Intercepts({@Signature(
        method = "query",
        type = Executor.class,
        args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})})
public class SearchInterceptor implements Interceptor {

    private static final String PATTERN_URL = "like\\s?\\n?\\s+CONCAT\\s?\\(\\s?('%'\\s?,\\s?)?\\s?#\\s?\\{.*}\\s?(\\s?,\\s?'%')?\\s?\\)";
    private String databaseType;                                                                                                                // 数据库类型，不同的数据库有不同的分页方法
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
    private static final String ROOT_SQL_NODE = "sqlSource.rootSqlNode";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object parameter = invocation.getArgs()[1];
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        MetaObject metaMappedStatement = MetaObject.forObject(statement, DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        BoundSql boundSql = statement.getBoundSql(parameter);
        if (metaMappedStatement.hasGetter(ROOT_SQL_NODE)) {
            //修改参数值
            SqlNode sqlNode = (SqlNode) metaMappedStatement.getValue(ROOT_SQL_NODE);
            getBoundSql(statement.getConfiguration(), boundSql.getParameterObject(), sqlNode);
        }
        return invocation.proceed();
    }

    /**
     * 获取原始sql
     *
     * @param configuration   配置
     * @param parameterObject 参数
     * @param sqlNode         sql文件节点
     * @return BoundSql sql对象
     */
    private static BoundSql getBoundSql(Configuration configuration, Object parameterObject, SqlNode sqlNode) {
        DynamicContext context = new DynamicContext(configuration, parameterObject);
        sqlNode.apply(context);
        String countextSql = context.getSql();
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
        String sql = modifyLikeSql(countextSql, parameterObject);
        SqlSource sqlSource = sqlSourceParser.parse(sql, parameterType, context.getBindings());

        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        for (Map.Entry<String, Object> entry : context.getBindings().entrySet()) {
            boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
        }
        return boundSql;
    }

    /**
     * like语句处理
     *
     * @param sql             sql串
     * @param parameterObject 参数
     * @return String sql字符串
     */
    private static String modifyLikeSql(String sql, Object parameterObject) {
        Map<String, Object> paramMab;
        if (parameterObject instanceof Map) {
            paramMab = (Map) parameterObject;
        }
        //        else if (parameterObject instanceof Page) {
        //            paramMab = ((Page)parameterObject).getParams();
        //        }
        else {
            return sql;
        }
        if (!sql.toLowerCase().contains("like"))
            return sql;
        Pattern pattern = Pattern.compile(PATTERN_URL, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);

        List<String> replaceFiled = new ArrayList<>();

        while (matcher.find()) {
            int n = matcher.groupCount();
            for (int i = 0; i <= n; i++) {
                String output = matcher.group(i);
                if (output != null) {
                    String key = getParameterKey(output);
                    if (replaceFiled.indexOf(key) < 0) {
                        replaceFiled.add(key);
                    }
                }
            }
        }
        //修改参数
        for (String key : replaceFiled) {
            Object val = paramMab.get(key);
            if (val != null && val instanceof String
                    && (val.toString().contains("%") || val.toString().contains("_"))) {
                val = val.toString().replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
                paramMab.put(key, val);
            }

        }
        return sql;
    }

    /**
     * 截取key
     *
     * @param input 输入字符串
     * @return String key值
     */
    private static String getParameterKey(String input) {
        if (input.contains(".")) {
            return input.substring(input.indexOf(".") + 1, input.indexOf("}"));
        }
        return input;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.databaseType = properties.getProperty(databaseType);
    }
}
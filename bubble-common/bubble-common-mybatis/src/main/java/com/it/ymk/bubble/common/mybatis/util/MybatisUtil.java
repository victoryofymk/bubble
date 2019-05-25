package com.it.ymk.bubble.common.mybatis.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * @author yanmingkun
 * @date 2019-05-25 16:45
 */
public class MybatisUtil {
    /**
     * 插入数据库
     *
     * @param emp
     * @return
     */
    public int insertEnterprise(List<Map> emp) {
        String resource = "mybaits-config.xml";
        Reader reader = null;
        SqlSessionFactory ssf = null;
        SqlSession session = null;
        int count = 0;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            ssf = builder.build(reader);
            session = ssf.openSession();
            count = session.insert("com.org.position.dao.employeeDao.insertEnterprise", emp);
            session.commit();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            session.close();
        }
        return count;
    }
}

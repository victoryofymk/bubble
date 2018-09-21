package com.it.ymk.bubble.test.controller;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.it.ymk.bubble.Application;
import com.it.ymk.bubble.core.mybatis.annotation.User;

/**
 * @author yanmingkun
 * @date 2018-09-21 15:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate       redisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testObj() throws Exception {
        User user = new User();
        user.setUserId("aa");
        user.setUsername("aa");
        user.setPassword("aa123456");
        user.setMobileNum("123");

        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set("com.it.ymk.bubble.core.mybatis.annotatio", user);
        operations.set("com.it.ymk.bubble.core.mybatis.annotatio.f", user, 1, TimeUnit.SECONDS);
        Thread.sleep(1000);
        //redisTemplate.delete("com.neo.f");
        boolean exists = redisTemplate.hasKey("com.neo.f");
        if (exists) {
            System.out.println("exists is true");
        }
        else {
            System.out.println("exists is false");
        }
        // Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
    }
}

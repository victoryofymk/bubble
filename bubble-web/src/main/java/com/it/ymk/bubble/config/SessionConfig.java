package com.it.ymk.bubble.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author yanmingkun
 * @date 2018-09-21 16:20
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400 * 30)
public class SessionConfig {
}

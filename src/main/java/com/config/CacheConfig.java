package com.config;



import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 2017/12/7.
 */
@Configuration
@EnableCaching
public class CacheConfig {
    /*ConcurrentMapCacheManager 是一个简单的缓存管理器，但是他的缓存存储是基于内存的，只适合开发，测试或基础的应用*/
   /* @Bean
    public CacheManager cacheManager(){
        return new ConcurrentMapCacheManager();
    }*/

   /*ehCache pom文件*/
  /* @Bean
   public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){

       EhCacheManagerFactoryBean ehCacheManagerFactoryBean=new EhCacheManagerFactoryBean();
       ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("enCache.xml"));
       ehCacheManagerFactoryBean.setShared(true);
       return ehCacheManagerFactoryBean;
   }*/

   /*@Bean
   public JCacheManagerFactoryBean jCacheManagerFactoryBean(){
       JCacheManagerFactoryBean jCacheManagerFactoryBean=new JCacheManagerFactoryBean();
       return jCacheManagerFactoryBean;
   }*/

  /* @Bean
    public EhCacheCacheManager cacheManager(net.sf.ehcache.CacheManager cacheManager){
        return new EhCacheCacheManager(cacheManager);
    }*/

    /*redis cache*/
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate){
        RedisCacheManager redisCacheManager=new RedisCacheManager(redisTemplate);
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("animal",300L);//为animal（key）设置过期时间（s）
        redisCacheManager.setExpires(map);
        return redisCacheManager;
    }

   /*CompositeCacheManager 使用多个缓存管理器*/
   /*@Bean
    public CacheManager cacheManager(net.sf.ehcache.CacheManager ehCacheManager, RedisTemplate redisTemplate){
       CompositeCacheManager compositeCacheManager=new CompositeCacheManager();
       List<CacheManager> cacheManagers=new ArrayList<CacheManager>();

       cacheManagers.add(new EhCacheCacheManager(ehCacheManager));
       cacheManagers.add(new RedisCacheManager(redisTemplate));
       compositeCacheManager.setCacheManagers(cacheManagers);
       return compositeCacheManager;
    }*/
}

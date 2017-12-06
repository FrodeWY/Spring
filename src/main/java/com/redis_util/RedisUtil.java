package com.redis_util;

import com.redis_domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


/**
 * Created by mac on 2017/12/6.
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,Product> redisTemplate;

    public Product saveValue(Product product){
        redisTemplate.opsForValue().set(product.getSku(),product);
        return  redisTemplate.opsForValue().get(product.getSku());
    }

    public Boolean deleteValue(Product product){
        redisTemplate.opsForValue().set(product.getSku(),product);
        DataType type = redisTemplate.type(product.getSku());
        redisTemplate.delete(product.getSku());

        Boolean hasKey = redisTemplate.hasKey(product.getSku());
        return hasKey;
    }

    public List<Product> saveListProduct(String key,Product[] products){
//        redisTemplate.opsForList().leftPush(key,product);
        redisTemplate.opsForList().leftPushAll(key,products);
        List<Product> range = redisTemplate.opsForList().range(key, 0L, -1L);
        return  range;
    }

    public Product popListProduct(String key){
        Product product = redisTemplate.opsForList().leftPop(key);
        return product;
    }

    public Set<Product> saveSetProduct(String key, Product[] products){
        Long add = redisTemplate.opsForSet().add(key, products);

        return redisTemplate.opsForSet().members(key);
    }
    public Set<Product> productSet(String key,String key2){
        Set<Product> difference = redisTemplate.opsForSet().difference(key, key2);
        Set<Product> intersect = redisTemplate.opsForSet().intersect(key, key2);
        Set<Product> union = redisTemplate.opsForSet().union(key, key2);
        return difference;
    }
    public Long removeSet(String key,Product product){
        Long remove = redisTemplate.opsForSet().remove(key, product);
        return remove;
    }
    public Product randomSetProduct(String key){
        Product randomMember = redisTemplate.opsForSet().randomMember(key);
        return randomMember;
    }

    public void saveBoundListOperations(String key,Product[] product){
        BoundListOperations<String,Product> boundListOperations=redisTemplate.boundListOps(key);
        boundListOperations.leftPushAll(product);
    }
}

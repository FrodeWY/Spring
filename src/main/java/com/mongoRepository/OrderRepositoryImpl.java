package com.mongoRepository;

import com.mongo_domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by mac on 2017/12/6.
 */
public class OrderRepositoryImpl implements OrderOperations {
    @Autowired
    private MongoOperations  mongoOperations;
    @Override
    public List<Order> findOrderByType(String type) {
        String s = type.equals("web") ? "toy" : type;
        List<Order> orders = mongoOperations.find(Query.query(Criteria.where("type").is(s)), Order.class);
        return orders;
    }
}

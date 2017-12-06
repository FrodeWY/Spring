package com.mongoRepository;

import com.mongo_domain.Order;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mac on 2017/12/5.
 */
@Service
public class MongoOperationTest {
    @Autowired
    private MongoOperations mongo;
    public void saveOrder(Order order){
        mongo.save(order,"order");
    }
    public Long getCount(String field){
        long count = mongo.getCollection(field).count();
        return count;
    }
    public Order findOrderById(String id){
        Order order = mongo.findById(id, Order.class);
        return order;
    }
    public List<Order> query(String client){
        List<Order> orderList = mongo.find(Query.query(Criteria.where("customer").is(client)), Order.class);
        return orderList;
    }
    public void delete(String id){
        mongo.remove(Query.query(Criteria.where("id").is(id).and("type").is("toy")), Order.class);
    }
}

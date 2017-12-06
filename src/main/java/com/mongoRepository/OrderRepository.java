package com.mongoRepository;

import com.mongo_domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by mac on 2017/12/6.
 */

public interface OrderRepository extends MongoRepository<Order,String>,OrderOperations{
    List<Order> findByType(String type);
    @Query("{'customer':'nan','type':'toy','id':?0}")
    Order findOneById(String id);

}

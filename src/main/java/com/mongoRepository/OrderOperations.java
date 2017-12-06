package com.mongoRepository;

import com.mongo_domain.Order;

import java.util.List;

/**
 * Created by mac on 2017/12/6.
 */
public interface OrderOperations {
    List<Order> findOrderByType(String type);
}

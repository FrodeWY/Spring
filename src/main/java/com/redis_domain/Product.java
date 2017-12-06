package com.redis_domain;

import java.io.Serializable;

/**
 * Created by mac on 2017/12/6.
 */
public class Product implements Serializable{
    private static final long serialVersionUID = 1L;

    private String sku;
    private String name;
    private float price;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

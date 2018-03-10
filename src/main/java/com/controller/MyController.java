package com.controller;

import com.domain.Animal;
import com.mongoRepository.MongoOperationTest;
import com.mongoRepository.OrderRepository;
import com.mongo_domain.Item;
import com.mongo_domain.Order;
import com.redis_domain.Product;
import com.redis_util.RedisUtil;
import com.repository.AnimalRepository;
import com.service.AnimalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by K on 2017/12/3.
 */
@Controller

public class MyController {
    private AnimalRepository animalRepository;
    private AnimalService animalService;
    private MongoOperationTest mongoOperations;
    private OrderRepository  orderRepository;
    private RedisUtil redisUtil;

    public MyController(AnimalRepository animalRepository, AnimalService animalService,
                        MongoOperationTest mongoOperations, OrderRepository orderRepository, RedisUtil redisUtil) {
        this.animalRepository = animalRepository;
        this.animalService = animalService;
        this.mongoOperations = mongoOperations;
        this.orderRepository = orderRepository;
        this.redisUtil = redisUtil;
    }

    @RequestMapping("/hello")
    @Transactional
    public String toHello(){
        List<Animal> all = animalRepository.findAll();
        List<Animal> byAgeBetween = animalRepository.findByAgeBetween(10, 13);
        List<Animal> animals=animalRepository.findByNameNotAndNameStartingWith("nina","n");
        List<Animal> distinctAnimal = animalRepository.findDistinctAnimalByNameStartingWith("n");
        int count =animalRepository.countByName("Nina");//不区分大小写？
        List<Animal> ageAscNameDesc = animalRepository.findByNameStartingWithOrderByAgeAscNameDesc("n");//多个属性排序
        List<Animal> animalLikeNa = animalRepository.findAnimalLikeNa();
        int pig = animalRepository.updateAnimalType("pig", 1l);
        /*updataAnimal(),虽然没有被animalRepository实现，但是jpa为repository实现接口时，会查找名字与接口相同，且添加了Impl后缀的类，如果这个
        * 类存在，spring data jpa 会将它的方法与spring Data jpa 所生成的方法合并在一起， AnimalRepository 要查找的就是AnimalRepositoryImpl*/
        int i = animalRepository.updataAnimal();
        List<Animal> first2ByNameStartingWith = animalRepository.findFirst2ByNameStartingWith("n");//查找前两个结果
        List<Animal> byNameAndType = animalRepository.findAnimalNamedQuery("nina", "cat");//NamedQuery
     /*   Calendar calendar=Calendar.getInstance();
        calendar.set(1993,10,10);
        Date startTime = calendar.getTime();
        calendar.set(1995,11,10);
        Date endTime=calendar.getTime();
        Pageable pageable= new PageRequest(0,5);
        Page<Animal> animalPage = animalService.DynamicFindAnimal("nina", 12, "bird", startTime, endTime, pageable);*/
        System.out.println("end");
        return "hello";
    }

    @RequestMapping("/findOneAnimal")
    public @ResponseBody Animal findAnimal(Animal animal){
        Animal animal1 = animalService.findAnimal(animal);
        return animal1;
    }

    @RequestMapping("/saveAnimal")
    public @ResponseBody Animal saveAnimal(Animal animal){
        Animal animal1 = animalService.saveAnimal(animal);
        return animal1;
    }
    @RequestMapping("/unlessFindOne")
    public @ResponseBody Animal findOneUnless(Long id){
        Animal oneUnless = animalService.findOneUnless(id);
        return oneUnless;
    }
    @RequestMapping("/unlessAndConditionFindOne")
    public @ResponseBody Animal unlessAndConditionFindOne(Long id){
        Animal oneCondition = animalService.findOneCondition(id);
        return oneCondition;
    }

    @RequestMapping("/removeAnimal")
    public @ResponseBody void removeAnimal(Long id){
        animalService.remove(id);
    }

    @RequestMapping("mongo")
    public String toMongo(){
        LinkedHashSet linkedHashSet=new LinkedHashSet();

        Item item=new Item(1L,"s",11.16,10);
        Item item2=new Item(2L,"a",12.16,12);
        Order order=new Order("8","nan","toy");
        linkedHashSet.add(item);
        linkedHashSet.add(item2);
        order.setItems(linkedHashSet);
//        mongoOperations.saveOrder(order);
        Order save = orderRepository.save(order);
        List<Order> orderList = orderRepository.findByType("toy");
        List<Order> orders = orderRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        Long order1 = mongoOperations.getCount("order");
        Order orderById =orderRepository.findOneById("2");
        List<Order> byType = orderRepository.findOrderByType("web");
        return "mongo";
    }

    @RequestMapping("/redis")
    public String redis(){
        Product product=new Product();
        product.setSku("pro1");
        product.setName("车");
        product.setPrice(1234.122f);
        Product product1 = redisUtil.saveValue(product);
        Product product2=new Product();
        product2.setSku("pro2");
        Boolean deleteValue = redisUtil.deleteValue(product2);

        List<Product> cars = redisUtil.saveListProduct("car", (Product[]) Arrays.asList(product, product2).toArray());
        Product popListProduct = redisUtil.popListProduct("car");

        Product product3=new Product();
        product3.setSku("pro3");
        Product product4=new Product();
        product4.setSku("pro4");
        Set<Product> car1 = redisUtil.saveSetProduct("car1", (Product[]) Arrays.asList(product1, product2, product3).toArray());
        Set<Product> car2 = redisUtil.saveSetProduct("car2", (Product[]) Arrays.asList(product1, product2, product3, product4).toArray());
        Set<Product> set = redisUtil.productSet("car1", "car2");
        Long removeSet = redisUtil.removeSet("car1", product3);
        redisUtil.saveBoundListOperations("car4", (Product[]) Arrays.asList(product1,product3,product4).toArray());
        return "hello";
    }

}

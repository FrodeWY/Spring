package com.controller;

import com.domain.Animal;
import com.repository.AnimalRepository;
import com.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by K on 2017/12/3.
 */
@Controller
public class MyController {
    private AnimalRepository animalRepository;
    private AnimalService animalService;

    public MyController(AnimalRepository animalRepository, AnimalService animalService) {
        this.animalRepository = animalRepository;
        this.animalService = animalService;
    }

    @RequestMapping("/hello")
    @Transactional
    public String toHello(){
      /*  List<Animal> all = animalRepository.findAll();
        List<Animal> byAgeBetween = animalRepository.findByAgeBetween(10, 13);
        List<Animal> animals=animalRepository.findByNameNotAndNameStartingWith("nina","n");
        List<Animal> distinctAnimal = animalRepository.findDistinctAnimalByNameStartingWith("n");
        int count =animalRepository.countByName("Nina");//不区分大小写？
        List<Animal> ageAscNameDesc = animalRepository.findByNameStartingWithOrderByAgeAscNameDesc("n");//多个属性排序
        List<Animal> animalLikeNa = animalRepository.findAnimalLikeNa();
        int pig = animalRepository.updateAnimalType("pig", 1l);
        *//*updataAnimal(),虽然没有被animalRepository实现，但是jpa为repository实现接口时，会查找名字与接口相同，且添加了Impl后缀的类，如果这个
        * 类存在，spring data jpa 会将它的方法与spring Data jpa 所生成的方法合并在一起， AnimalRepository 要查找的就是AnimalRepositoryImpl*//*
        int i = animalRepository.updataAnimal();
        List<Animal> first2ByNameStartingWith = animalRepository.findFirst2ByNameStartingWith("n");//查找前两个结果
        List<Animal> byNameAndType = animalRepository.findAnimalNamedQuery("nina", "cat");//NamedQuery*/
        Calendar calendar=Calendar.getInstance();
        calendar.set(1993,10,10);
        Date startTime = calendar.getTime();
        calendar.set(1995,11,10);
        Date endTime=calendar.getTime();
        Pageable pageable= new PageRequest(0,5);
        Page<Animal> animalPage = animalService.DynamicFindAnimal("nina", 12, "bird", startTime, endTime, pageable);
        System.out.println("end");
        return "hello";
    }
}

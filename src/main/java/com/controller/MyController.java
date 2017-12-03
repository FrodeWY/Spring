package com.controller;

import com.domain.Animal;
import com.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by K on 2017/12/3.
 */
@Controller
public class MyController {
private AnimalRepository animalRepository;

    public MyController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @RequestMapping("/Hello")
    public String toHello(){
        List<Animal> all = animalRepository.findAll();

        return "hello";
    }
}

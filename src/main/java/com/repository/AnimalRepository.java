package com.repository;

import com.domain.Animal;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Created by K on 2017/12/3.
 */

public interface AnimalRepository extends JpaRepository<Animal,Long>{

}

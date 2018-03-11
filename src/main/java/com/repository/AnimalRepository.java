package com.repository;

import com.domain.Animal;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by K on 2017/12/3.
 */

public interface AnimalRepository extends JpaRepository<Animal,Long>,AnimalExtend,JpaSpecificationExecutor{
    List<Animal>findByAgeBetween(int start,int end);
    List<Animal>findByNameNotAndNameStartingWith(String notName,String name);
    List<Animal> findDistinctAnimalByNameStartingWith(String name);
    int countByName(String name);
    List<Animal>findByNameStartingWithOrderByAgeAscNameDesc(String name);
    @Query("select a from Animal a where name like '%na%'")
    List<Animal>findAnimalLikeNa();

    @Modifying
    @Query("update Animal a set a.type= :type where a.id= :id")
    int updateAnimalType(@Param("type") String type,@Param("id") Long id);

    List<Animal>findFirst2ByNameStartingWith(String name);

    List<Animal>findAnimalNamedQuery(@Param("name") String name,@Param("type") String type);
}

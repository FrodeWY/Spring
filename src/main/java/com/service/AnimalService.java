package com.service;

import com.domain.Animal;
import com.repository.AnimalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2017/12/4.
 */
@Service
public class AnimalService {

    private AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }
    @Transactional
    public Page<Animal>DynamicFindAnimal(final String name, final Integer age, final String type, final Date  startTime, final Date endTime, final Pageable pageable){
        Calendar calendar=Calendar.getInstance();
        calendar.set(1993,10,10);
       final Date startTime2=calendar.getTime();
        Page animalPage = animalRepository.findAll(new Specification<Animal>() {
            @Override
            public Predicate toPredicate(Root<Animal> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                List<Expression<Boolean>> expressions=predicate.getExpressions();
                if(name!=null){
                    expressions.add(cb.equal(root.<String>get("name"),name));
                }
                if(age!=null){
                    expressions.add(cb.between(root.<Integer>get("age"),11,15));
                }
                if(type!=null){
                    expressions.add(cb.equal(root.<String>get("type"),type));
                }
                if(startTime!=null){
                    expressions.add(cb.greaterThanOrEqualTo(root.<Date>get("birthday"),startTime2));
                }
                if(endTime!=null){
                    expressions.add(cb.lessThanOrEqualTo(root.<Date>get("birthday"),endTime));
                }
                criteriaQuery.where(predicate);
                criteriaQuery.orderBy(cb.desc(root.<Integer>get("age")));
                return criteriaQuery.getRestriction();
            }
        },pageable);
        return animalPage;
    }
}

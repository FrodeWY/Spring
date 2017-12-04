package com.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by mac on 2017/12/4.
 */
public class AnimalRepositoryImpl implements AnimalExtend{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int updataAnimal() {
        String updataSql="update Animal a set a.type='bird' where a.id in (1,3,4)";
        int i = entityManager.createQuery(updataSql).executeUpdate();
        return i;

    }
}

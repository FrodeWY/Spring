package com.repository;

import com.domain.Error;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRepository  extends JpaRepository<Error,Integer>{
    Error findByCode(Integer code);
}

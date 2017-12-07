package com.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by K on 2017/12/3.
 */
@Entity
@NamedQuery(name = "Animal.findAnimalNamedQuery",query = "select a from Animal a where a.name=:name and a.type =:type")
public class Animal implements Serializable{
    private static final Long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Integer age;

    private String name;

    private Date birthday;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

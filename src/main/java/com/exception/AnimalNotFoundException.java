package com.exception;

public class AnimalNotFoundException extends RuntimeException {
    private Integer code;
    private Long id;

    public AnimalNotFoundException(Integer code, Long id) {
        this.code = code;
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

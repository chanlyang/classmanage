package com.jsut.classmanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jsut.classmanage.mapper")
public class ClassManageBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassManageBackendApplication.class, args);
    }

}

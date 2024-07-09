package com.aia.crud.application;

import com.aia.crud.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.aia.crud")
@EnableJpaRepositories(basePackages = "com.aia.crud")
@SpringBootApplication(scanBasePackages = "com.aia.crud")
public class CRUDRestApplication  {

  @Autowired
  private BookService service;

  public static void main(String[] args) {
    SpringApplication.run(CRUDRestApplication.class, args);
  }


}

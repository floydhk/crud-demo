package com.aia.crud.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "book")
public class Book {

  @Id
  @GeneratedValue
  private Long id;
  private String author;
  private String title;
  private Boolean published;

  @Builder
  public Book(Long id, String author, String title, Boolean published) {
    this.id = id;
    this.author = author;
    this.title = title;
    this.published = published;
  }

}

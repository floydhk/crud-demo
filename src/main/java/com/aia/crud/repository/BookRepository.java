package com.aia.crud.repository;

import com.aia.crud.model.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorAndPublished(String author, Boolean published);
    List<Book> findByAuthor(String author);
    List<Book> findByPublished(Boolean published);

}

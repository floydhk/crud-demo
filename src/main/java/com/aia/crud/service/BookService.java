package com.aia.crud.service;

import java.util.List;
import java.util.Optional;

import com.aia.crud.model.domain.Book;
import com.aia.crud.model.dto.BookDTO;

public interface BookService {

    Long createNewBook(BookDTO dto);

    List<Optional<BookDTO>> getAllBooks();
    Optional<BookDTO> getBookById(Long id);

    List<Book> findByAuthorAndPublished(String author, Boolean published);
    List<Book> findByAuthor(String author);
    List<Book> findByPublished(Boolean published);

    void deleteBookById(Long id);


}

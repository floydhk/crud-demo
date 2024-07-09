package com.aia.crud.service.impl;

import com.aia.crud.builder.BookBuilder;
import com.aia.crud.exception.BookNotFoundException;
import com.aia.crud.model.domain.Book;
import com.aia.crud.model.dto.BookDTO;
import com.aia.crud.repository.BookRepository;
import com.aia.crud.service.BookService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookBuilder builder;

    @Override
    public Long createNewBook(BookDTO dto) {
        return Stream.of(dto)
            .map(builder::build)
            .map(repository::save)
            .map(Book::getId)
            .findFirst()
            .get();
    }

    @Override
    public List<Optional<BookDTO>> getAllBooks() {
        return repository.findAll()
                .stream()
                .map(builder::build)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<BookDTO> getBookById(Long id) {
        return repository.findById(id)
            .map(builder::build)
            .orElseThrow(() -> new BookNotFoundException(String.format("No such Book for id '%s'", id)));
    }


    @Override
    public List<Book> findByAuthorAndPublished(String author, Boolean published) {
        return repository.findByAuthorAndPublished(author, published);
    }


    @Override
    public List<Book> findByAuthor(String author) {
        return repository.findByAuthor(author);
    }


    @Override
    public List<Book> findByPublished(Boolean published) {
        return repository.findByPublished(published);
    }


    @Override
    public void deleteBookById(Long id) {
        repository.deleteById(id);
    }


}

package com.aia.crud.builder;

import java.util.Optional;

import com.aia.crud.model.domain.Book;
import com.aia.crud.model.dto.BookDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BookBuilder {

    private final ModelMapper modelMapper;

    public Book build(BookDTO dto) {
        Book model = modelMapper.map(dto, Book.class);
        return model;
    }

    public Optional<BookDTO> build(Book domain) {
        BookDTO dto = modelMapper.map(domain, BookDTO.class);
        return Optional.of(dto);
    }

    public Book build(BookDTO dto, Book domain) {
        modelMapper.map(dto, domain);
        return domain;
    }

}

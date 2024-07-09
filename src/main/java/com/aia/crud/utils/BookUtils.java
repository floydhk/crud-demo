package com.aia.crud.utils;

import com.aia.crud.model.domain.Book;
import com.aia.crud.model.dto.BookDTO;

public class BookUtils {

    public static BookDTO createBookDto(String author, String title, Boolean published) {
        return BookDTO.builder()
            .author(author)
            .title(title)
            .published(published)
            .build();
    }


    public static Book createBook(String author, String title, Boolean published) {
        return Book.builder()
                .author(author)
                .title(title)
                .published(published)
                .build();
    }
}

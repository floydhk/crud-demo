package com.aia.crud.controller;

import com.aia.crud.exception.BookNotFoundException;
import com.aia.crud.model.domain.Book;
import com.aia.crud.model.dto.BookDTO;
import com.aia.crud.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService service;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@Valid @RequestBody BookDTO dto, UriComponentsBuilder uriComponentsBuilder) {
        Long bookId = service.createNewBook(dto);
        UriComponents uriComponents = uriComponentsBuilder
            .path("/api/v1/book/create/{id}")
            .buildAndExpand(bookId);

//        HttpHeaders headers = new HttpHeaders();
  //      headers.setLocation(uriComponents.toUri());
        Map<String, Long> respondBody = new HashMap<>();
        respondBody.put("bookId", bookId);

        return ResponseEntity.created(uriComponents.toUri()).body(respondBody);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAll() {

        List<Optional<BookDTO>> books=service.getAllBooks();

        if (books.isEmpty()) {
            throw new BookNotFoundException("Book Not found");
        }


        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBookById(id));
    }

    @GetMapping("/search")
    @ResponseBody
    public  ResponseEntity<?> findBy(@RequestParam Optional<String> author, @RequestParam Optional<Boolean> published)  {
        List<Book> books = null;

        if (author.isPresent() && published.isPresent()) {
           books = service.findByAuthorAndPublished(author.get(), published.get());
        } else if (published.isPresent()) {
           books = service.findByPublished(published.get());
        } else if (author.isPresent()) {
           books = service.findByAuthor(author.get());
        }

        if (books.isEmpty()) {
            throw new BookNotFoundException("Book Not found with author = " + author.get());

        }

        return ResponseEntity.ok(books);
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteBookById(id);

        Map<String, Long> respondBody = new HashMap<>();
        respondBody.put("bookId", id);
        return ResponseEntity.ok().body(respondBody);
    }

}

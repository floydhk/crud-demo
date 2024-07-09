package com.aia.crud.controller;

import com.aia.crud.application.CRUDRestApplication;
import com.aia.crud.exception.BookNotFoundException;
import com.aia.crud.model.domain.Book;
import com.aia.crud.model.dto.BookDTO;
import com.aia.crud.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aia.crud.utils.BookUtils.createBook;
import static com.aia.crud.utils.BookUtils.createBookDto;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = CRUDRestApplication.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService service;

    @Captor
    private ArgumentCaptor<BookDTO> argumentCaptor;

    private final static String urlTemplate = "/api/v1/book";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Order(1)
    @DisplayName("Create New Book Successfully")
    public void createNewBookSuccessfully() throws Exception {
        Optional<BookDTO> dto = Optional.of(createBookDto("Gary", "Java Programming", Boolean.TRUE));

        when(service.createNewBook(argumentCaptor.capture()))
            .thenReturn(1L);

        mockMvc.perform(post(urlTemplate+ "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated());

        assertThat(argumentCaptor.getValue().getAuthor(), is("Gary"));
        assertThat(argumentCaptor.getValue().getTitle(), is("Java Programming"));
        assertThat(argumentCaptor.getValue().getPublished(), is(Boolean.TRUE));
    }

    @Test
    @Order(2)
    @DisplayName("Get All Books with 2 items Successfully")
    public void getAllBooksAndWithTwoItemsSuccessfully() throws Exception {
        List<Optional<BookDTO>> stub = Arrays.asList(
                createBookDto("Gary", "Java", Boolean.TRUE),
                createBookDto("Tommy", "Python", Boolean.FALSE))
                .stream()
                .map(Optional::of)
                .collect(Collectors.toList());

        when(service.getAllBooks())
                .thenReturn(stub);

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].author", is("Gary")))
                .andExpect(jsonPath("$[0].published", is(Boolean.TRUE)))
                .andExpect(jsonPath("$[1].author", is("Tommy")))
                .andExpect(jsonPath("$[1].title", is("Python")));
    }


    @Test
    @Order(3)
    @DisplayName("Get Book By Id Successfully")
    public void getBookByIdSuccessfully() throws Exception {
        Optional<BookDTO> stub = Optional.of(createBookDto("Mary", "Microservice", Boolean.TRUE));

        when(service.getBookById(1L))
            .thenReturn(stub);

        mockMvc.perform(get(urlTemplate + "/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.author", is("Mary")))
            .andExpect(jsonPath("$.title", is("Microservice")))
            .andExpect(jsonPath("$.published", is(Boolean.TRUE)));
    }


    @Test
    @Order(4)
    @DisplayName("Get Book By Author or Published Successfully")
    public void getBookByAuthorOrPublishedSuccessfully() throws Exception {
        List<Book> stub = Arrays.asList(createBook("Mary", "Microservice", Boolean.TRUE));

        when(service.findByAuthor("Mary"))
                .thenReturn(stub);

        mockMvc.perform(get(urlTemplate + "/search?author="+ "Mary"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].author", is("Mary")))
                .andExpect(jsonPath("$[0].title", is("Microservice")))
                .andExpect(jsonPath("$[0].published", is(Boolean.TRUE)));

        // find by published
        when(service.findByPublished(Boolean.TRUE))
                .thenReturn(stub);

        mockMvc.perform(get(urlTemplate + "/search?published=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].author", is("Mary")))
                .andExpect(jsonPath("$[0].title", is("Microservice")))
                .andExpect(jsonPath("$[0].published", is(Boolean.TRUE)));



    }


    @Test
    @Order(5)
    @DisplayName("Get Book By Author and Published status Successfully")
    public void getBookByAuthorAndPublishedSuccessfully() throws Exception {
        List<Book> stub = Arrays.asList(createBook("Mary", "Microservice", Boolean.TRUE));

        when(service.findByAuthorAndPublished("Mary", Boolean.TRUE))
                .thenReturn(stub);

        mockMvc.perform(get(urlTemplate + "/search?author=Mary&published=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].author", is("Mary")))
                .andExpect(jsonPath("$[0].title", is("Microservice")))
                .andExpect(jsonPath("$[0].published", is(Boolean.TRUE)));
    }



    @Test
    @Order(6)
    @DisplayName("Delete Book By Id Successfully")
    public void deleteBookByIdSuccessfully() throws Exception {

        mockMvc.perform(delete(urlTemplate + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.bookId", is(1)));
    }


    @Test
    @Order(7)
    @DisplayName("Get BookNotFoundException Error")
    public void getBookNotFoundException() throws Exception {
        List<Book> stub = Arrays.asList(createBook("Mary", "Microservice", Boolean.TRUE));

        when(service.findByAuthor("Peter"))
                .thenThrow(new BookNotFoundException("No such Book for Peter"));

        mockMvc.perform(get(urlTemplate + "/search?author="+ "Peter"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

}

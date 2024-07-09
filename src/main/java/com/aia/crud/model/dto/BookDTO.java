package com.aia.crud.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
public class BookDTO {

    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Author must contain alphanumeric characters only")
    @JsonProperty("author")
    private String author;

    @Pattern(regexp = "^[a-zA-Z0-9 ]+$*", message = "Title must contain alphanumeric characters only")
    @JsonProperty("title")
    private String title;

    @NotNull
    @JsonProperty("published")
    private Boolean published;

    @Builder
    public BookDTO(String author, String title, Boolean published) {
        this.author = author;
        this.title = title;
        this.published = published;
    }
}

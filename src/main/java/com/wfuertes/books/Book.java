package com.wfuertes.books;

import lombok.Value;
import lombok.experimental.Accessors;

import java.time.Instant;

@Value
@Accessors(fluent = true)
public class Book {

    String id;
    String title;
    String author;
    String isbn;
    int rating;
    Instant createdAt;
    Instant updatedAt;

}

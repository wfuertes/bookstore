package com.wfuertes.books;

import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class Book {

    String id;
    String title;
    String author;
    String isbn;
    int rating;
    long createdAt;
    long updatedAt;

}

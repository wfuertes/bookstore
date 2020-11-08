package com.wfuertes.books;

import lombok.Value;
import lombok.experimental.Accessors;

import java.time.Instant;

@Value
@Accessors(fluent = true)
public class BookPrice {

    String bookId;
    int price;
    Instant createdAt;
    Instant updatedAt;
}

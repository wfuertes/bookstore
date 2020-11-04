package com.wfuertes.books;

import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Optional;

@Value
@Accessors(fluent = true)
public class PageSummary<T> {

    Collection<T> items;
    Optional<String> nextPage;
}

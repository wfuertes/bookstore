package com.wfuertes.books;

import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;

@Value
@Accessors(fluent = true)
public class Page<T> implements Iterator<Page<T>> {

    Collection<T> items;
    Optional<NextPage> nextPage;

    @Override
    public boolean hasNext() {
        return nextPage.isPresent();
    }

    @Override
    public Page<T> next() {
        return (Page<T>) nextPage.get().page.get();
    }

    @Value
    @Accessors(fluent = true)
    public static class NextPage<T> {
        String link;
        int number;
        int limit;
        Supplier<Page<T>> page;
    }
}

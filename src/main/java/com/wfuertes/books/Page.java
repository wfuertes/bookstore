package com.wfuertes.books;

import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Value
@Accessors(fluent = true)
public class Page<T> implements Iterable<Page<T>> {

    int number;
    int limit;
    Collection<T> items;
    Supplier<Page<T>> nextPage;

    public Page(int number, int limit, Collection<T> items, Supplier<Page<T>> nextPage) {
        this.number = number;
        this.limit = limit;
        this.items = items;
        this.nextPage = nextPage;
    }

    public Page(int number, int limit, Collection<T> items) {
        this(number, limit, items, null);
    }

    public Optional<String> nextPageLink(String baseUrl) {
        if (nextPage != null) {
            return Optional.of(String.format("%s?page=%d&limit=%d", baseUrl, number + 1, limit));
        }
        return Optional.empty();
    }

    @Override
    public Iterator<Page<T>> iterator() {
        return new PageIterator<>(new Page<>(number, limit, items, nextPage));
    }

    public Stream<T> itemsStream() {
        return StreamSupport.stream(this.spliterator(), false)
                            .flatMap(page -> page.items.stream());
    }

    public static class PageIterator<T> implements Iterator<Page<T>> {

        private Page<T> page;
        private final AtomicBoolean isFirstPage;

        public PageIterator(Page<T> page) {
            this.page = page;
            this.isFirstPage = new AtomicBoolean(page.number == 1);
        }

        @Override
        public boolean hasNext() {
            return isFirstPage.get() || page.nextPage() != null;
        }

        @Override
        public Page<T> next() {
            if (isFirstPage.get()) {
                isFirstPage.set(false);
                return page;
            }

            if (page.nextPage() != null) {
                page = page.nextPage.get();
                return page;
            }
            throw new IllegalStateException("Invalid state for PageIterator");
        }
    }
}

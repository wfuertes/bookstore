package com.wfuertes.books;

import java.util.stream.Stream;

public interface BookRepository {

    Page<Book> findAll(PageQuery pageQuery);

    default Stream<Book> bookStream(PageQuery pageQuery) {
        return findAll(pageQuery).itemsStream();
    }

    void save(Stream<BookPrice> bookPriceStream);

    Page<BookPrice> findPrices(PageQuery pageQuery);
}

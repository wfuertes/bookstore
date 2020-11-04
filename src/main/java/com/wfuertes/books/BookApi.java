package com.wfuertes.books;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;

public class BookApi {

    private final BookService bookService;

    @Inject
    public BookApi(BookService bookService) {
        this.bookService = bookService;
    }

    public PageSummary<Book> findAll(PageQuery pageQuery) {
        return bookService.findAll(pageQuery);
    }

    public ByteArrayOutputStream downloadCSV() {
        return bookService.downloadCSV();
    }
}

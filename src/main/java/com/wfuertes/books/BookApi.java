package com.wfuertes.books;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;

public class BookApi {

    private final BookRepository repository;
    private final BookService bookService;

    @Inject
    public BookApi(BookRepository repository, BookService bookService) {
        this.repository = repository;
        this.bookService = bookService;
    }

    public Page<Book> findAll(PageQuery pageQuery) {
        return repository.findAll(pageQuery);
    }

    public ByteArrayOutputStream downloadCSV() {
        return bookService.downloadCSV();
    }
}

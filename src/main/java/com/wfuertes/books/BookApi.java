package com.wfuertes.books;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.CompletableFuture;

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

    public CompletableFuture<Void> processPrices(PageQuery pageQuery) {
        return bookService.processBookPrices(pageQuery);
    }

    public PageSummary<BookPrice> findPrices(PageQuery pageQuery) {
        return bookService.findPrices(pageQuery);
    }
}

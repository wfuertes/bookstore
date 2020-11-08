package com.wfuertes.books;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class BookService {

    private final BookRepository repository;

    @Inject
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public PageSummary<Book> findAll(PageQuery query) {
        final Page<Book> page = repository.findAll(query);
        return new PageSummary<>(page.items(), page.nextPageLink("/books"));
    }

    public ByteArrayOutputStream downloadCSV() {
        final Page<Book> bookPage = repository.findAll(new PageQuery(1, 100));

        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final PrintStream printStream = new PrintStream(outputStream);
            final CSVPrinter printer = new CSVPrinter(printStream, CSVFormat.DEFAULT.withHeader(
                    "id", "title", "author", "isbn", "rating", "createdAt", "updatedAt"
            ));

            bookPage.itemsStream()
                    .forEach(book -> {
                        try {
                            printer.printRecord(book.id(),
                                                book.title(),
                                                book.author(),
                                                book.isbn(),
                                                book.rating(),
                                                book.createdAt(),
                                                book.updatedAt());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            printer.flush();
            return outputStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<Void> processBookPrices(PageQuery initialPageQuery) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            final Runnable task = () -> {
                final Stream<BookPrice> bookPriceStream = repository.bookStream(initialPageQuery)
                                                                    .map(book -> new BookPrice(book.id(),
                                                                                               ThreadLocalRandom.current()
                                                                                                                .nextInt(1000, 5000),
                                                                                               Instant.now(),
                                                                                               Instant.now()));

                repository.save(bookPriceStream);
            };
            return CompletableFuture.runAsync(task, executorService);
        } finally {
            executorService.shutdown();
        }
    }

    public PageSummary<BookPrice> findPrices(PageQuery pageQuery) {
        final Page<BookPrice> page = repository.findPrices(pageQuery);
        return new PageSummary<>(page.items(), page.nextPageLink("/books/prices"));
    }
}

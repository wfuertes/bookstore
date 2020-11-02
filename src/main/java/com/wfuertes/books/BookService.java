package com.wfuertes.books;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;

public class BookService {

    private final BookRepository repository;

    @Inject
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public ByteArrayOutputStream downloadCSV() {
        Page<Book> bookPage = repository.findAll(new PageQuery(1, 100));
        Collection<Book> books = new LinkedList<>(bookPage.items());

        while (bookPage.hasNext()) {
            bookPage = bookPage.next();
            books.addAll(bookPage.items());
        }

        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final PrintStream printStream = new PrintStream(outputStream);
            final CSVPrinter printer = new CSVPrinter(printStream, CSVFormat.DEFAULT.withHeader(
                    "id", "title", "author", "isbn", "rating", "createdAt", "updatedAt"
            ));

            for (Book book : books) {
                printer.printRecord(book.id(),
                                    book.title(),
                                    book.author(),
                                    book.isbn(),
                                    book.rating(),
                                    book.createdAt(),
                                    book.updatedAt());
            }

            printer.flush();
            return outputStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.wfuertes.books;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.wfuertes.bookstore.sql.Tables.BOOKS;
import static com.wfuertes.bookstore.sql.Tables.BOOK_PRICES;
import static java.time.ZoneOffset.UTC;

public class SqlBookRepository implements BookRepository {

    private final DSLContext context;

    @Inject
    public SqlBookRepository(DSLContext context) {
        this.context = context;
    }

    @Override
    public Page<Book> findAll(final PageQuery query) {
        final List<Book> books = context.selectFrom(BOOKS)
                                        .limit(query.limit() + 1)
                                        .offset(query.offset())
                                        .fetch(booksRecord -> new Book(booksRecord.getId(),
                                                                       booksRecord.getTitle(),
                                                                       booksRecord.getAuthor(),
                                                                       booksRecord.getIsbn(),
                                                                       booksRecord.getRating(),
                                                                       booksRecord.getCreatedAt().atOffset(UTC).toInstant(),
                                                                       booksRecord.getUpdatedAt().atOffset(UTC).toInstant()));

        if (books.size() > query.limit()) {
            final PageQuery nextQuery = new PageQuery(query.page() + 1, query.limit());
            final Supplier<Page<Book>> nextPage = () -> findAll(nextQuery);

            return new Page<>(query.page(),
                              query.limit(),
                              books.stream().limit(query.limit()).collect(Collectors.toList()),
                              nextPage);
        }

        return new Page<>(query.page(), query.limit(), books);
    }

    @Override
    public void save(Stream<BookPrice> bookPriceStream) {
        try (bookPriceStream) {
            context.transaction(config -> DSL.using(config)
                                             .batch(bookPriceStream.map(bookPrice -> DSL.mergeInto(BOOK_PRICES)
                                                                                        .columns(BOOK_PRICES.BOOK_ID,
                                                                                                 BOOK_PRICES.PRICE,
                                                                                                 BOOK_PRICES.CREATED_AT,
                                                                                                 BOOK_PRICES.UPDATED_AT)
                                                                                        .values(bookPrice.bookId(),
                                                                                                bookPrice.price(),
                                                                                                bookPrice.createdAt()
                                                                                                         .atOffset(UTC)
                                                                                                         .toLocalDateTime(),
                                                                                                bookPrice.updatedAt()
                                                                                                         .atOffset(UTC)
                                                                                                         .toLocalDateTime()))
                                                                   .collect(Collectors.toList()))
                                             .execute()
            );
        }
    }

    @Override
    public Page<BookPrice> findPrices(PageQuery query) {
        final List<BookPrice> bookPrices = context.selectFrom(BOOK_PRICES)
                                                  .limit(query.limit() + 1)
                                                  .offset(query.offset())
                                                  .fetch(record -> new BookPrice(record.getBookId(),
                                                                                 record.getPrice(),
                                                                                 record.getCreatedAt().atOffset(UTC).toInstant(),
                                                                                 record.getUpdatedAt().atOffset(UTC).toInstant()));

        if (bookPrices.size() > query.limit()) {
            final PageQuery nextQuery = new PageQuery(query.page() + 1, query.limit());
            final Supplier<Page<BookPrice>> nextPage = () -> findPrices(nextQuery);

            return new Page<>(query.page(),
                              query.limit(),
                              bookPrices.stream().limit(query.limit()).collect(Collectors.toList()),
                              nextPage);
        }

        return new Page<>(query.page(), query.limit(), bookPrices);
    }
}

package com.wfuertes.books;

import org.jooq.DSLContext;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.wfuertes.bookstore.sql.Tables.BOOKS;
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
}

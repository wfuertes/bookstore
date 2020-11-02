package com.wfuertes.books;

import org.jooq.DSLContext;

import javax.inject.Inject;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.wfuertes.bookstore.sql.Tables.BOOKS;

public class SqlBookRepository implements BookRepository {

    private final DSLContext context;

    @Inject
    public SqlBookRepository(DSLContext context) {
        this.context = context;
    }

    @Override
    public Page<Book> findAll(PageQuery query) {
        final List<Book> books = context.selectFrom(BOOKS)
                                        .limit(query.limit() + 1)
                                        .offset(query.offset())
                                        .fetch(booksRecord -> new Book(booksRecord.getId(),
                                                                       booksRecord.getTitle(),
                                                                       booksRecord.getAuthor(),
                                                                       booksRecord.getIsbn(),
                                                                       booksRecord.getRating(),
                                                                       booksRecord.getCreatedAt()
                                                                                  .atOffset(ZoneOffset.UTC)
                                                                                  .toInstant()
                                                                                  .toEpochMilli(),
                                                                       booksRecord.getUpdatedAt()
                                                                                  .atOffset(ZoneOffset.UTC)
                                                                                  .toInstant()
                                                                                  .toEpochMilli()));

        Page.NextPage<Book> nextPage = null;
        if (books.size() > query.limit()) {
            nextPage = new Page.NextPage<>(String.format("/books?page=%d&limit=%d", query.page() + 1, query.limit()),
                                           query.page() + 1,
                                           query.limit(),
                                           () -> findAll(new PageQuery(query.page() + 1, query.limit())));
        }

        return new Page<>(books.stream().limit(query.limit()).collect(Collectors.toList()), Optional.ofNullable(nextPage));
    }
}

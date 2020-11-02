package com.wfuertes.books;

public interface BookRepository {

    Page<Book> findAll(PageQuery pageQuery);
}

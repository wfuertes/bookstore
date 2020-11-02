package com.wfuertes.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.wfuertes.books.BookRepository;
import com.wfuertes.books.SqlBookRepository;

import java.util.Optional;

public class BookStoreModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Gson.class).toProvider(() -> new GsonBuilder().registerTypeAdapter(Optional.class, new OptionalAdapter())
                                                           .create()).in(Scopes.SINGLETON);

        bind(BookRepository.class).to(SqlBookRepository.class).in(Scopes.SINGLETON);
    }
}

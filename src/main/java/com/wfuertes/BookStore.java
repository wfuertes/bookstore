package com.wfuertes;


import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.wfuertes.config.BookStoreModule;
import com.wfuertes.config.DatabaseModule;
import io.logz.guice.jersey.JerseyModule;
import io.logz.guice.jersey.JerseyServer;
import io.logz.guice.jersey.configuration.JerseyConfiguration;

import java.util.List;

public class BookStore {
    public static void main(String[] args) throws Exception {
        JerseyConfiguration configuration = JerseyConfiguration.builder()
                                                               .addPackage("com.wfuertes.rest")
                                                               .addPort(8088)
                                                               .build();

        final List<Module> modules = ImmutableList.<Module>builder().add(new JerseyModule(configuration))
                                                                    .add(DatabaseModule.local()) // for PROD use production()
                                                                    .add(new BookStoreModule())
                                                                    .build();

        Guice.createInjector(modules).getInstance(JerseyServer.class).start();
    }
}

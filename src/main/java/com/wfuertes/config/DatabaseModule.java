package com.wfuertes.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DatabaseModule extends AbstractModule {

    private static final Settings JOOQ_SETTINGS = new Settings().withRenderNameCase(RenderNameCase.LOWER)
                                                                .withRenderSchema(false);
    private final DataSource dataSource;

    private DatabaseModule(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DatabaseModule(Provider<DataSource> dataSource) {
        this(dataSource.get());
    }

    public static DatabaseModule production() {
        return new DatabaseModule(new DataSourceProvider());
    }

    @Override
    protected void configure() {
        final Flyway flyway = flyway();
        flyway.migrate();

        bind(DataSource.class).toInstance(flyway.getConfiguration().getDataSource());
    }

    private Flyway flyway() {
        return Flyway.configure().table("schema_history").dataSource(dataSource).load();
    }

    @Provides
    @Singleton
    private DSLContext dslContext(DataSource dataSource) {
        try {
            return DSL.using(dataSource.getConnection(), SQLDialect.MYSQL, JOOQ_SETTINGS);
        } catch (SQLException err) {
            throw new RuntimeException("Fail to provide a DSLContext", err);
        }
    }

    private static class DataSourceProvider implements Provider<DataSource> {

        @Override
        public DataSource get() {
            final HikariConfig config = new HikariConfig();
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.setJdbcUrl("jdbc:mysql://localhost:3306/bookstore?useSSL=false&serverTimezone=UTC");
            config.setUsername("root");
            config.setPassword("root");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            return new HikariDataSource(config);
        }
    }
}

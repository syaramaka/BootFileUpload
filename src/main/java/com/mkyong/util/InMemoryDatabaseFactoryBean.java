package com.mkyong.util;
import org.h2.tools.SimpleResultSet;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

public class InMemoryDatabaseFactoryBean implements FactoryBean<DataSource>, DisposableBean {
    private final DataSource dataSource;

    public InMemoryDatabaseFactoryBean(String... sqlFiles) {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.setName("H2");
        builder.setType(EmbeddedDatabaseType.H2);
        for (String file : sqlFiles) {
            builder.addScript(file);
        }
        dataSource = builder.build();
    }

    @Override
    public DataSource getObject() throws Exception {
        return dataSource;
    }

    @Override
    public Class<?> getObjectType() {
        return DataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {
        DriverManager.getConnection("jdbc:h2:mem:").close();
    }
}

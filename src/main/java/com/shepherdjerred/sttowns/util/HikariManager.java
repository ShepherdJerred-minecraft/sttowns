package com.shepherdjerred.sttowns.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariManager {

    private final HikariDataSource hikariDataSource;

    public HikariManager(HikariConfig hikariConfig) {
        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public HikariDataSource getDataSource() {
        return hikariDataSource;
    }

}

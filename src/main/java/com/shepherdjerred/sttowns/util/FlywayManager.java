package com.shepherdjerred.sttowns.util;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class FlywayManager {

    private static final List<String> migrationNames = new ArrayList<>();
    private final Flyway flyway = new Flyway();

    static {
        migrationNames.add("V1__Initial");
    }

    public void migrate(DataSource dataSource) {
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }

}

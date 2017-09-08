package utils;

import com.shepherdjerred.sttowns.util.FlywayManager;
import com.shepherdjerred.sttowns.util.HikariManager;
import com.zaxxer.hikari.HikariConfig;

public class DatabaseUtils {

    private static final FlywayManager flywayFacade = new FlywayManager();
    private static final HikariManager hikariFacade = new HikariManager(new HikariConfig("src/test/resources/hikari.properties"));

    public static HikariManager getHikariFacade() {
        return hikariFacade;
    }

    public static FlywayManager getFlywayFacade() {
        return flywayFacade;
    }

    static {
        flywayFacade.migrate(hikariFacade.getDataSource());
    }

}

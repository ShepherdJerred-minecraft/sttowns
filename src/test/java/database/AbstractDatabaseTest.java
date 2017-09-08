package database;

import com.shepherdjerred.sttowns.util.FlywayManager;
import com.shepherdjerred.sttowns.util.HikariManager;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.junit.After;
import org.junit.Before;
import utils.DatabaseUtils;

public class AbstractDatabaseTest {

    protected HikariManager hikariFacade = DatabaseUtils.getHikariFacade();
    protected FluentJdbc fluentJdbc = new FluentJdbcBuilder().connectionProvider(hikariFacade.getDataSource()).build();
    protected FlywayManager flywayFacade = DatabaseUtils.getFlywayFacade();

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }

}

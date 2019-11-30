package dk.sunepoulsen.analysethis.persistence;

import dk.sunepoulsen.adopt.core.environment.Environment;
import dk.sunepoulsen.adopt.core.environment.EnvironmentException;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class PersistenceMigration {
    private static Logger log = LoggerFactory.getLogger( PersistenceMigration.class );
    private Environment environment;

    public PersistenceMigration( Environment environment ) {
        this.environment = environment;
    }

    public void migrate() {
        try {
            if( "false".equalsIgnoreCase( environment.getString( "liquibase.migrate", "true" ) ) ) {
                return;
            }

            logDatabaseDrivers();
            String url = environment.getString( "datasource.url" );
            String username = environment.getString( "datasource.user" );
            String password = environment.getString( "datasource.password" );

            log.info( "Start migration of {}", url );

            try( Connection connection = DriverManager.getConnection( url, username, password ) ) {
                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation( new JdbcConnection( connection ) );
                Liquibase liquibase = new Liquibase( "db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database );

                liquibase.update( new Contexts(), new LabelExpression() );
            }
            catch( LiquibaseException | SQLException ex ) {
                log.error( "Unable to migrate {}: {}", url, ex.getMessage(), ex );
            }
        }
        catch( EnvironmentException ex ) {
            log.warn( "Unable to migrate liquibase database: {}", ex.getMessage() );
        }
    }

    private void logDatabaseDrivers() {
        log.debug( "Supported JDBC drivers:" );
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while( drivers.hasMoreElements() ) {
            Driver driver = drivers.nextElement();
            log.debug( driver.getClass().getName() );
        }
    }
}

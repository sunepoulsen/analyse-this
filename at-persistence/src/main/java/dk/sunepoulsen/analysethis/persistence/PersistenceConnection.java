package dk.sunepoulsen.analysethis.persistence;

import dk.sunepoulsen.adopt.core.environment.Environment;
import dk.sunepoulsen.adopt.core.environment.EnvironmentException;
import dk.sunepoulsen.analysethis.persistence.services.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class PersistenceConnection {
    private static Logger log = LoggerFactory.getLogger( PersistenceConnection.class );

    private String propertiesPrefix;
    private Environment environment;
    private EntityManagerFactory emf;

    PersistenceConnection( Environment environment ) {
        this( environment, "datasource" );
    }

    PersistenceConnection( Environment environment, String propertiesPrefix ) {
        this.environment = environment;
        this.propertiesPrefix = propertiesPrefix;
    }

    public String persistenceName() throws EnvironmentException {
        return environment.getString( propertiesPrefix + ".persistence.name" );
    }

    void connect() throws EnvironmentException {
        Map<String, String> properties = new HashMap<>();

        environment.stream()
            .filter( entry -> entry.getKey().startsWith( propertiesPrefix + "." ) )
            .forEach( entry -> properties.put( entry.getKey().substring( propertiesPrefix.length() + 1 ), entry.getValue().toString() ) );

        log.debug( "Connecting to database {}", persistenceName() );
        this.emf = Persistence.createEntityManagerFactory( persistenceName(), properties );
    }

    public void disconnect() {
        if( this.emf.isOpen() ) {
            try {
                log.debug( "Disconnecting from database {}", persistenceName() );
            }
            catch( EnvironmentException ex ) {
                log.warn( "Disconnecting from database unknown: {}", ex.getMessage(), ex );
            }
            this.emf.close();
        }
    }

    public boolean isOpen() {
        return this.emf.isOpen();
    }

    public EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    public RepositoryService createRepositoryService() {
        return new RepositoryService( this );
    }
}

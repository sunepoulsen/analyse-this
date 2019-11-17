package dk.sunepoulsen.analysethis.persistence;

import dk.sunepoulsen.adopt.core.environment.Environment;
import dk.sunepoulsen.adopt.core.environment.EnvironmentException;

public class PersistenceFactory {
    private Environment environment;

    public PersistenceFactory( Environment environment ) {
        this.environment = environment;
    }

    public PersistenceConnection createPersistence() throws EnvironmentException {
        PersistenceMigration migration = new PersistenceMigration( this.environment );
        migration.migrate();

        PersistenceConnection persistenceConnection = new PersistenceConnection( this.environment );
        persistenceConnection.connect();

        return persistenceConnection;
    }
}

package dk.sunepoulsen.analysethis.persistence.modules;

import dk.sunepoulsen.adopt.core.environment.Environment;
import dk.sunepoulsen.adopt.core.environment.EnvironmentException;
import dk.sunepoulsen.adopt.core.registry.api.Inject;
import dk.sunepoulsen.adopt.core.registry.api.RegistryException;
import dk.sunepoulsen.adopt.core.registry.api.binder.RegistryInstanceProvider;
import dk.sunepoulsen.analysethis.persistence.PersistenceConnection;
import dk.sunepoulsen.analysethis.persistence.PersistenceMigration;

public class PersistenceConnectionProvider implements RegistryInstanceProvider<PersistenceConnection> {
    private Environment environment;

    @Inject
    public PersistenceConnectionProvider( Environment environment ) {
        this.environment = environment;
    }

    @Override
    public PersistenceConnection getInstance() throws RegistryException  {
        try {
            PersistenceMigration migration = new PersistenceMigration( this.environment );
            migration.migrate();

            PersistenceConnection persistenceConnection = new PersistenceConnection( this.environment );
            persistenceConnection.connect();

            return persistenceConnection;
        }
        catch( EnvironmentException ex ) {
            throw new RegistryException( ex.getMessage(), ex );
        }
    }
}

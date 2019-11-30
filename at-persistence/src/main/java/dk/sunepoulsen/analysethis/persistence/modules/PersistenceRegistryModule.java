package dk.sunepoulsen.analysethis.persistence.modules;

import dk.sunepoulsen.adopt.core.registry.api.AbstractRegistryModule;
import dk.sunepoulsen.adopt.core.registry.api.RegistryException;
import dk.sunepoulsen.analysethis.persistence.PersistenceConnection;
import dk.sunepoulsen.analysethis.persistence.services.RepositoryService;

public class PersistenceRegistryModule extends AbstractRegistryModule {
    @Override
    protected void configure() throws RegistryException {
        bind( PersistenceConnection.class ).toProvider( PersistenceConnectionProvider.class );
        bind( RepositoryService.class ).to( RepositoryService.class );
    }
}

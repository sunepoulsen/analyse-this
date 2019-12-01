package dk.sunepoulsen.analysethis.persistence.services;

import dk.sunepoulsen.analysethis.persistence.PersistenceConnection;
import dk.sunepoulsen.analysethis.persistence.entities.RepositoryEntity;

public interface RepositoryService {
    PersistenceConnection getPersistenceConnection();
    RepositoryEntity persistRepository( RepositoryEntity entity );
}

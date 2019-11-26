package dk.sunepoulsen.analysethis.persistence.services;

import dk.sunepoulsen.analysethis.persistence.PersistenceConnection;
import dk.sunepoulsen.analysethis.persistence.entities.RepositoryEntity;

import javax.persistence.TypedQuery;

public class RepositoryService extends AbstractPersistenceService {
    public RepositoryService( PersistenceConnection persistenceConnection ) {
        super( persistenceConnection );
    }

    public RepositoryEntity persistRepository( RepositoryEntity entity ) {
        return transactional( entityManager -> {
            TypedQuery<RepositoryEntity> namedQuery = entityManager.createNamedQuery( "findRepository", RepositoryEntity.class );
            namedQuery.setParameter( "project", entity.getProject() );
            namedQuery.setParameter( "name", entity.getName() );

            RepositoryEntity foundEntity = find( namedQuery );
            if( foundEntity == null ) {
                foundEntity = new RepositoryEntity();
            }

            foundEntity.setVcs( entity.getVcs() );
            foundEntity.setProject( entity.getProject() );
            foundEntity.setName( entity.getName() );
            foundEntity.setDescription( entity.getDescription() );
            foundEntity.setCloneUrl( entity.getCloneUrl() );
            foundEntity.setAnalysedAt( entity.getAnalysedAt() );

            entityManager.persist(foundEntity);

            return foundEntity;
        } );
    }
}

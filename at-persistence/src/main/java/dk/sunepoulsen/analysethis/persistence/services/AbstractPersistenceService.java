package dk.sunepoulsen.analysethis.persistence.services;

import dk.sunepoulsen.analysethis.persistence.PersistenceConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.function.Function;

public class AbstractPersistenceService {
    private static Logger log = LoggerFactory.getLogger( AbstractPersistenceService.class );

    private final PersistenceConnection persistenceConnection;

    AbstractPersistenceService( final PersistenceConnection persistenceConnection ) {
        this.persistenceConnection = persistenceConnection;
    }

    <T> T find( TypedQuery<T> query ) {
        List<T> entities = query.getResultList();
        if( entities.isEmpty() ) {
            return null;
        }

        return entities.get( 0 );
    }

    <T> T transactional( Function<EntityManager, T> function ) {
        EntityManager em = persistenceConnection.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T result = function.apply( em );
            tx.commit();

            return result;
        }
        catch( Exception ex ) {
            if( tx != null && tx.isActive() ) {
                log.warn( "Rollback transaction because of exception.", ex );
                tx.rollback();
            }

            throw ex;
        }
        finally {
            em.close();
        }
    }
}

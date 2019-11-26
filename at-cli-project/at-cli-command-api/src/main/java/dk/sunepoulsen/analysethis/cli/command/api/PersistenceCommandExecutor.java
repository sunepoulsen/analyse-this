package dk.sunepoulsen.analysethis.cli.command.api;

import dk.sunepoulsen.adopt.cli.command.api.CliException;
import dk.sunepoulsen.adopt.cli.command.api.CommandExecutor;
import dk.sunepoulsen.adopt.core.environment.EnvironmentException;
import dk.sunepoulsen.analysethis.persistence.PersistenceConnection;
import dk.sunepoulsen.analysethis.persistence.PersistenceFactory;

public abstract class PersistenceCommandExecutor implements CommandExecutor {
    private PersistenceFactory persistenceFactory;

    public PersistenceCommandExecutor( PersistenceFactory persistenceFactory ) {
        this.persistenceFactory = persistenceFactory;
    }

    @Override
    public void validateArguments() throws CliException {
    }

    @Override
    public void performAction() throws CliException {
        try {
            PersistenceConnection persistenceConnection = persistenceFactory.createPersistence();
            doPerformAction( persistenceConnection );
            persistenceConnection.disconnect();

        }
        catch( EnvironmentException ex ) {
            throw new CliException( ex.getMessage(), ex );
        }
    }

    protected abstract void doPerformAction( PersistenceConnection persistenceConnection ) throws CliException;
}

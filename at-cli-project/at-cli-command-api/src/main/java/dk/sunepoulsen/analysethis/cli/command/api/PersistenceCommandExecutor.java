package dk.sunepoulsen.analysethis.cli.command.api;

import dk.sunepoulsen.adopt.cli.command.api.CliException;
import dk.sunepoulsen.adopt.cli.command.api.CommandExecutor;
import dk.sunepoulsen.analysethis.persistence.PersistenceConnection;

public abstract class PersistenceCommandExecutor implements CommandExecutor {
    private PersistenceConnection persistenceConnection;

    public PersistenceCommandExecutor( PersistenceConnection persistenceConnection ) {
        this.persistenceConnection = persistenceConnection;
    }

    @Override
    public void validateArguments() throws CliException {
    }

    @Override
    public void performAction() throws CliException {
        doPerformAction( persistenceConnection );
        persistenceConnection.disconnect();

    }

    protected abstract void doPerformAction( PersistenceConnection persistenceConnection ) throws CliException;
}

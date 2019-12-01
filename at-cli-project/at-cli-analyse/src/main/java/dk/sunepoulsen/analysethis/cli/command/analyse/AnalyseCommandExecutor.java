package dk.sunepoulsen.analysethis.cli.command.analyse;

import dk.sunepoulsen.adopt.cli.command.api.CliException;
import dk.sunepoulsen.adopt.cli.command.api.CommandExecutor;
import dk.sunepoulsen.adopt.core.registry.api.Inject;
import dk.sunepoulsen.analysethis.cli.command.api.PersistenceCommandExecutor;
import dk.sunepoulsen.analysethis.persistence.PersistenceConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AnalyseCommandExecutor extends PersistenceCommandExecutor {
    private static Logger consoleLogger = LoggerFactory.getLogger( CommandExecutor.CONSOLE_LOGGER_NAME );
    private static Logger log = LoggerFactory.getLogger( AnalyseCommandExecutor.class );

    private List<String> repoNames;

    @Inject
    public AnalyseCommandExecutor( PersistenceConnection persistenceConnection ) {
        super(persistenceConnection);
    }

    public List<String> getRepoNames() {
        return repoNames;
    }

    public void setRepoNames( List<String> repoNames ) {
        this.repoNames = repoNames;
    }

    @Override
    protected void doPerformAction( PersistenceConnection persistenceConnection ) throws CliException {
        consoleLogger.info( "This command is not implemented yet!" );
    }
}

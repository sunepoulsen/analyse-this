package dk.sunepoulsen.analysethis.cli.command.analyse;

import dk.sunepoulsen.adopt.cli.command.api.CliException;
import dk.sunepoulsen.adopt.cli.command.api.CommandExecutor;
import dk.sunepoulsen.adopt.core.environment.Environment;
import dk.sunepoulsen.adopt.core.environment.EnvironmentException;
import dk.sunepoulsen.analysethis.persistence.PersistenceConnection;
import dk.sunepoulsen.analysethis.persistence.PersistenceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AnalyseCommandExecutor implements CommandExecutor {
    private static Logger consoleLogger = LoggerFactory.getLogger( CommandExecutor.CONSOLE_LOGGER_NAME );
    private static Logger log = LoggerFactory.getLogger( AnalyseCommandExecutor.class );

    private Environment environment;
    private List<String> repoNames;

    AnalyseCommandExecutor( Environment environment, List<String> repoNames ) {
        this.environment = environment;
        this.repoNames = repoNames;
    }

    @Override
    public void validateArguments() throws CliException {
    }

    @Override
    public void performAction() throws CliException {
        PersistenceConnection persistenceConnection = createPersistenceConnection();

        try {
            consoleLogger.info( "This command is not implemented yet!" );
        }
        finally {
            persistenceConnection.disconnect();
        }
    }


    private PersistenceConnection createPersistenceConnection() throws CliException {
        PersistenceFactory persistenceFactory = new PersistenceFactory( this.environment );

        try {
            return persistenceFactory.createPersistence();
        }
        catch( EnvironmentException ex ) {
            throw new CliException( "Unable to connect to database: " + ex.getMessage(), ex );
        }

    }
}

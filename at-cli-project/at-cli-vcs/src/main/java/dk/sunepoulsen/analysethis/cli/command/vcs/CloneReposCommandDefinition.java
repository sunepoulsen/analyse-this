package dk.sunepoulsen.analysethis.cli.command.vcs;

import dk.sunepoulsen.adopt.cli.command.api.CliException;
import dk.sunepoulsen.adopt.cli.command.api.CommandDefinition;
import dk.sunepoulsen.adopt.cli.command.api.CommandExecutor;
import dk.sunepoulsen.adopt.core.environment.Environment;
import dk.sunepoulsen.analysethis.git.GitClientException;
import dk.sunepoulsen.analysethis.persistence.PersistenceFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class CloneReposCommandDefinition implements CommandDefinition {
    @Override
    public String name() {
        return "clone-repos";
    }

    @Override
    public String description() {
        return "Clone one or more repositories";
    }

    @Override
    public Options options() {
        return new Options();
    }

    @Override
    public CommandExecutor createExecutor( CommandLine commandLine ) throws CliException {
        try {
            PersistenceFactory persistenceFactory = new PersistenceFactory( new Environment() );

            return new CloneReposCommandExecutor(persistenceFactory, commandLine.getArgList());
        }
        catch( GitClientException ex ) {
            throw new CliException( ex.getMessage(), ex );
        }
    }
}

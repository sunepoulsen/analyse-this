package dk.sunepoulsen.analysethis.cli.command.vcs;

import dk.sunepoulsen.adopt.cli.command.api.CliException;
import dk.sunepoulsen.adopt.cli.command.api.CommandDefinition;
import dk.sunepoulsen.adopt.cli.command.api.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class ListReposCommandDefinition implements CommandDefinition {
    @Override
    public String name() {
        return "list-repos";
    }

    @Override
    public String description() {
        return "List all repositories";
    }

    @Override
    public Options options() {
        return new Options();
    }

    @Override
    public CommandExecutor createExecutor( CommandLine commandLine ) throws CliException {
        return new ListReposCommandExecutor();
    }
}

package dk.sunepoulsen.analysethis.cli.command.vcs;

import dk.sunepoulsen.adopt.cli.command.api.CliException;
import dk.sunepoulsen.adopt.cli.command.api.CommandDefinition;
import dk.sunepoulsen.adopt.cli.command.api.CommandExecutor;
import dk.sunepoulsen.adopt.core.registry.api.Inject;
import dk.sunepoulsen.adopt.core.registry.api.Registry;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class ListReposCommandDefinition implements CommandDefinition {
    private Registry registry;

    @Inject
    public ListReposCommandDefinition( Registry registry) {
        this.registry = registry;
    }

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
        return registry.newInstance(ListReposCommandExecutor.class);
    }
}

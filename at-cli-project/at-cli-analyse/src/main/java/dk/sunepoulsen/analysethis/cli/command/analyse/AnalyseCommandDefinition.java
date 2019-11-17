package dk.sunepoulsen.analysethis.cli.command.analyse;

import dk.sunepoulsen.adopt.cli.command.api.CliException;
import dk.sunepoulsen.adopt.cli.command.api.CommandDefinition;
import dk.sunepoulsen.adopt.cli.command.api.CommandExecutor;
import dk.sunepoulsen.adopt.core.environment.Environment;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class AnalyseCommandDefinition implements CommandDefinition {
    @Override
    public String name() {
        return "analyse";
    }

    @Override
    public String description() {
        return "Analyse one or more repositories";
    }

    @Override
    public Options options() {
        return new Options();
    }

    @Override
    public CommandExecutor createExecutor( CommandLine commandLine ) throws CliException {
        return new AnalyseCommandExecutor( new Environment(), commandLine.getArgList());
    }
}

package dk.sunepoulsen.analysethis.cli.command.vcs;

import dk.sunepoulsen.adopt.cli.command.api.CliException;
import dk.sunepoulsen.adopt.cli.command.api.CommandExecutor;
import dk.sunepoulsen.analysethis.vcs.api.VCSClient;
import dk.sunepoulsen.analysethis.vcs.api.VCSException;
import dk.sunepoulsen.analysethis.vcs.api.VCSRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListReposCommandExecutor implements CommandExecutor {
    private static Logger consoleLogger = LoggerFactory.getLogger( CommandExecutor.CONSOLE_LOGGER_NAME );
    private static Logger log = LoggerFactory.getLogger( ListReposCommandExecutor.class );

    @Override
    public void validateArguments() throws CliException {
    }

    @Override
    public void performAction() throws CliException {
        VCSRegistry vcsRegistry = new VCSRegistry();

        List<VCSRepository> repositories = new ArrayList<>();
        vcsRegistry.stream()
            .filter(this::enabledClient)
            .forEach( vcsClient -> {
                try {
                    repositories.addAll( fetchRepositories( vcsClient ) );
                }
                catch( VCSException ex ) {
                    throw new RuntimeException( ex.getMessage(), ex );
                }
            } );

        repositories.stream()
            .sorted( Comparator.comparing( VCSRepository::getName ) )
            .forEach( this::printRepository );
    }

    private boolean enabledClient(VCSClient vcsClient) {
        try {
            return vcsClient.enabled();
        }
        catch( VCSException ex ) {
            log.warn( "Unable to check if VCS Client is enabled", ex );
            return false;
        }
    }

    private List<VCSRepository> fetchRepositories( VCSClient vcsClient ) throws VCSException {
        return vcsClient.fetchRepositories();
    }

    private void printRepository( VCSRepository repository ) {
        String projectName = repository.getProjectName();
        if( projectName == null ) {
            projectName = "<no project>";
        }

        String description = repository.getDescription();
        if( description == null ) {
            description = "<no description>";
        }

        consoleLogger.info( "{}/{}: {}", projectName, repository.getName(), description );
    }
}

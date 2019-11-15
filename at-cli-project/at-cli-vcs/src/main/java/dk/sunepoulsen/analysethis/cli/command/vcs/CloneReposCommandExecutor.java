package dk.sunepoulsen.analysethis.cli.command.vcs;

import dk.sunepoulsen.adopt.cli.command.api.CliException;
import dk.sunepoulsen.adopt.cli.command.api.CommandExecutor;
import dk.sunepoulsen.analysethis.git.GitClient;
import dk.sunepoulsen.analysethis.git.GitClientException;
import dk.sunepoulsen.analysethis.vcs.api.VCSClient;
import dk.sunepoulsen.analysethis.vcs.api.VCSException;
import dk.sunepoulsen.analysethis.vcs.api.VCSRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CloneReposCommandExecutor implements CommandExecutor {
    private static Logger consoleLogger = LoggerFactory.getLogger( CommandExecutor.CONSOLE_LOGGER_NAME );
    private static Logger log = LoggerFactory.getLogger( CloneReposCommandExecutor.class );

    private VCSRegistry vcsRegistry;
    private List<String> repoNames;
    private GitClient gitClient;

    public CloneReposCommandExecutor( List<String> repoNames ) throws GitClientException {
        this(new VCSRegistry(), repoNames, new GitClient());
    }

    public CloneReposCommandExecutor( VCSRegistry vcsRegistry, List<String> repoNames, GitClient gitClient ) {
        this.vcsRegistry = vcsRegistry;
        this.repoNames = repoNames;
        this.gitClient = gitClient;
    }

    @Override
    public void validateArguments() throws CliException {
    }

    @Override
    public void performAction() throws CliException {
        try {
            List<VCSRepository> repositories = new ArrayList<>();
            vcsRegistry.stream()
                .filter( this::enabledClient )
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
                .forEach( this::cloneRepository );
        }
        catch( RuntimeException ex ) {
            throw new CliException( ex.getMessage(), ex );
        }
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
        List<VCSRepository> repositories = vcsClient.fetchRepositories();

        if( repoNames != null && !repoNames.isEmpty()) {
            repositories.removeIf( vcsRepository -> !repoNames.contains( vcsRepository.getName() ) );
        }

        return repositories;
    }

    private void cloneRepository( VCSRepository repository ) {
        String projectName = repository.getProjectName();
        if( projectName == null ) {
            projectName = "<no project>";
        }

        consoleLogger.info( "Cloning repository {}/{}", projectName, repository.getName() );
        try {
            gitClient.cloneRepo( repository.getProjectName(), repository.getName(), repository.getCloneUrl() );
        }
        catch( IOException | InterruptedException ex ) {
            throw new RuntimeException( ex.getMessage(), ex );
        }
    }
}

package dk.sunepoulsen.analysethis.cli.command.vcs;

import dk.sunepoulsen.adopt.cli.command.api.CliException;
import dk.sunepoulsen.adopt.cli.command.api.CommandExecutor;
import dk.sunepoulsen.adopt.core.registry.api.Inject;
import dk.sunepoulsen.adopt.core.registry.api.Registry;
import dk.sunepoulsen.analysethis.cli.command.api.PersistenceCommandExecutor;
import dk.sunepoulsen.analysethis.git.GitClient;
import dk.sunepoulsen.analysethis.persistence.PersistenceConnection;
import dk.sunepoulsen.analysethis.persistence.entities.RepositoryEntity;
import dk.sunepoulsen.analysethis.persistence.services.RepositoryService;
import dk.sunepoulsen.analysethis.vcs.api.VCSClient;
import dk.sunepoulsen.analysethis.vcs.api.VCSException;
import dk.sunepoulsen.analysethis.vcs.api.VCSRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CloneReposCommandExecutor extends PersistenceCommandExecutor {
    private static Logger consoleLogger = LoggerFactory.getLogger( CommandExecutor.CONSOLE_LOGGER_NAME );
    private static Logger log = LoggerFactory.getLogger( CloneReposCommandExecutor.class );

    private List<String> repoNames;
    private List<VCSClient> vcsClients;
    private RepositoryService repositoryService;
    private GitClient gitClient;

    @Inject
    public CloneReposCommandExecutor( Registry registry, RepositoryService repositoryService, GitClient gitClient ) {
        super( repositoryService.getPersistenceConnection() );
        this.repoNames = null;
        this.vcsClients = registry.getInstances( VCSClient.class );
        this.repositoryService = repositoryService;
        this.gitClient = gitClient;
    }

    public List<String> getRepoNames() {
        return repoNames;
    }

    public void setRepoNames( List<String> repoNames ) {
        this.repoNames = repoNames;
    }

    @Override
    protected void doPerformAction( PersistenceConnection persistenceConnection ) throws CliException {
        try {
            List<VCSRepository> repositories = new ArrayList<>();
            vcsClients.stream()
                .filter( this::enabledClient )
                .forEach( vcsClient -> repositories.addAll( fetchRepositories( vcsClient ) ) );

            repositories.stream()
                .sorted( Comparator.comparing( VCSRepository::getName ) )
                .forEach( this::cloneRepository );
        }
        catch( VCSException ex ) {
            throw new CliException( ex.getMessage(), ex );
        }
    }

    private boolean enabledClient( VCSClient vcsClient ) {
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

        if( repoNames != null && !repoNames.isEmpty() ) {
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

            RepositoryEntity entity = new RepositoryEntity();
            entity.setVcs( repository.getVcs() );
            entity.setProject( repository.getProjectName() );
            entity.setName( repository.getName() );
            entity.setDescription( repository.getDescription() );
            entity.setCloneUrl( repository.getCloneUrl() );
            entity.setAnalysedAt( null );

            repositoryService.persistRepository( entity );
        }
        catch( IOException | InterruptedException ex ) {
            throw new RuntimeException( ex.getMessage(), ex );
        }
    }
}

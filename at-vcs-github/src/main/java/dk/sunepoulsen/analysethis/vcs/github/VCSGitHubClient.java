package dk.sunepoulsen.analysethis.vcs.github;

import dk.sunepoulsen.adopt.core.environment.Environment;
import dk.sunepoulsen.adopt.core.environment.EnvironmentException;
import dk.sunepoulsen.analysethis.vcs.api.VCSClient;
import dk.sunepoulsen.analysethis.vcs.api.VCSException;
import dk.sunepoulsen.analysethis.vcs.api.VCSRepository;
import dk.sunepoulsen.analysethis.vcs.github.environment.EnvironmentGitHubProperties;
import dk.sunepoulsen.analysethis.vcs.github.integration.GitHubIntegrator;
import dk.sunepoulsen.analysethis.vcs.github.integration.GitHubRepository;

import java.util.List;
import java.util.stream.Collectors;

public class VCSGitHubClient implements VCSClient {
    private Environment environment;
    private GitHubIntegrator integrator;

    public VCSGitHubClient() throws VCSException {
        this(new Environment(), null);
        this.integrator = createIntegrator();
    }

    public VCSGitHubClient( Environment environment, GitHubIntegrator integrator) {
        this.environment = environment;
        this.integrator = integrator;
    }

    @Override
    public boolean enabled() throws VCSException {
        try {
            return environment.getBoolean( EnvironmentGitHubProperties.GITHUB_ENABLED_KEY, false );
        }
        catch( EnvironmentException ex ) {
            throw new VCSException( ex.getMessage(), ex );
        }
    }

    @Override
    public List<VCSRepository> fetchRepositories() throws VCSException {
        try {
            return this.integrator.fetchRepositories().stream()
                .map( this::mapGitHubRepository )
                .collect( Collectors.toList() );
        }
        catch( RuntimeException ex ) {
            throw new VCSException( ex.getMessage(), ex );
        }
    }

    private VCSRepository mapGitHubRepository( GitHubRepository gitHubRepository ) {
        VCSRepository vcsRepository = new VCSRepository();

        vcsRepository.setName( gitHubRepository.getName() );
        vcsRepository.setDescription( gitHubRepository.getDescription() );

        if( gitHubRepository.getFullName() != null ) {
            int index = gitHubRepository.getFullName().indexOf( '/' );
            if( index < 0 ) {
                throw new RuntimeException( "Full name of repository is not in the expected format: " + gitHubRepository.getFullName() );
            }

            vcsRepository.setProjectName( gitHubRepository.getFullName().substring( 0, index ) );
        }
        vcsRepository.setCloneUrl( gitHubRepository.getCloneUrl() );

        return vcsRepository;
    }

    private GitHubIntegrator createIntegrator() throws VCSException {
        try {
            GitHubIntegrator integrator = new GitHubIntegrator();
            integrator.setBaseUrl( environment.getString( "vcs.github.url" ) );
            integrator.setConnectTimeout( Integer.parseInt( environment.getString( "vcs.connect.timeout" ) ) );
            integrator.setRequestTimeout( Integer.parseInt( environment.getString( "vcs.request.timeout" ) ) );
            integrator.setAuthorizationToken( environment.getString( "vcs.github.token" ) );

            return integrator;
        }
        catch( EnvironmentException ex ) {
            throw new VCSException( ex.getMessage(), ex );
        }

    }
}

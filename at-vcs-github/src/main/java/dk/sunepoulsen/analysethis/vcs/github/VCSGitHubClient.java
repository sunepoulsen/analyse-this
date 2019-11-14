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

    public VCSGitHubClient() {
        this.environment = new Environment();
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
        return createIntegrator().fetchRepositories().stream()
            .map( this::mapGitHubRepository )
            .collect( Collectors.toList() );
    }

    private VCSRepository mapGitHubRepository( GitHubRepository gitHubRepository ) {
        VCSRepository vcsRepository = new VCSRepository();

        vcsRepository.setName( gitHubRepository.getName() );
        vcsRepository.setDescription( gitHubRepository.getDescription() );

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

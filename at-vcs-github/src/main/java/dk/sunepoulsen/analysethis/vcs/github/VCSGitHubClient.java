package dk.sunepoulsen.analysethis.vcs.github;

import dk.sunepoulsen.adopt.core.environment.Environment;
import dk.sunepoulsen.adopt.core.environment.EnvironmentException;
import dk.sunepoulsen.analysethis.vcs.api.VCSClient;
import dk.sunepoulsen.analysethis.vcs.api.VCSException;
import dk.sunepoulsen.analysethis.vcs.api.VCSRepository;
import dk.sunepoulsen.analysethis.vcs.github.integration.GitHubIntegrator;
import dk.sunepoulsen.analysethis.vcs.github.integration.GitHubRepository;

import java.util.List;
import java.util.stream.Collectors;

public class VCSGitHubClient implements VCSClient {
    private Environment environment;
    private GitHubIntegrator integrator;

    public VCSGitHubClient() throws EnvironmentException {
        this.environment = new Environment();

        this.integrator = new GitHubIntegrator();
        this.integrator.setBaseUrl( environment.getString( "vcs.github.url" ) );
        this.integrator.setConnectTimeout( Integer.parseInt(environment.getString( "vcs.connect.timeout" ) ));
        this.integrator.setRequestTimeout( Integer.parseInt(environment.getString( "vcs.request.timeout" ) ));
        this.integrator.setAuthorizationToken( "69a2a7a2ebdb5857467d92be5d6d2ab8a8b81055" );
    }

    @Override
    public boolean enabled() throws VCSException {
        return true;
    }

    @Override
    public List<VCSRepository> fetchRepositories() throws VCSException {
        return this.integrator.fetchRepositories().stream()
            .map(this::mapGitHubRepository)
            .collect( Collectors.toList());
    }

    private VCSRepository mapGitHubRepository( GitHubRepository gitHubRepository ) {
        VCSRepository vcsRepository = new VCSRepository();

        vcsRepository.setName( gitHubRepository.getName() );
        vcsRepository.setDescription( gitHubRepository.getDescription() );

        return vcsRepository;
    }

}

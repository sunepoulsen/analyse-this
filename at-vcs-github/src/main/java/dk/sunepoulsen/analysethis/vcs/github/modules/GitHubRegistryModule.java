package dk.sunepoulsen.analysethis.vcs.github.modules;

import dk.sunepoulsen.adopt.core.registry.api.AbstractRegistryModule;
import dk.sunepoulsen.adopt.core.registry.api.RegistryException;
import dk.sunepoulsen.analysethis.vcs.api.VCSClient;
import dk.sunepoulsen.analysethis.vcs.github.VCSGitHubClient;
import dk.sunepoulsen.analysethis.vcs.github.integration.GitHubIntegrator;

public class GitHubRegistryModule extends AbstractRegistryModule {
    @Override
    protected void configure() throws RegistryException {
        bind( VCSClient.class ).to( VCSGitHubClient.class);
        bind( GitHubIntegrator.class ).to(GitHubIntegrator.class);
    }
}

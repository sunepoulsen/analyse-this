package dk.sunepoulsen.analysethis.git.modules;

import dk.sunepoulsen.adopt.core.registry.api.AbstractRegistryModule;
import dk.sunepoulsen.adopt.core.registry.api.RegistryException;
import dk.sunepoulsen.analysethis.git.GitClient;

public class GitRegistryModule extends AbstractRegistryModule {
    @Override
    protected void configure() throws RegistryException {
        bind( GitClient.class ).to( GitClient.class );
    }
}

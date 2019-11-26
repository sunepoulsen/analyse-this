package dk.sunepoulsen.analysethis.cli.command.vcs;

import dk.sunepoulsen.adopt.cli.command.api.CommandDefinition;
import dk.sunepoulsen.adopt.core.registry.api.AbstractRegistryModule;
import dk.sunepoulsen.adopt.core.registry.api.RegistryException;

public class VCSCommandModule extends AbstractRegistryModule {
    @Override
    protected void configure() throws RegistryException {
        bind( CommandDefinition.class ).to( ListReposCommandDefinition.class );
        bind( CommandDefinition.class ).to( CloneReposCommandDefinition.class );
    }
}

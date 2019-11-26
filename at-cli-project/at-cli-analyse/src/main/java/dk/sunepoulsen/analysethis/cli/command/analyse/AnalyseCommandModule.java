package dk.sunepoulsen.analysethis.cli.command.analyse;

import dk.sunepoulsen.adopt.cli.command.api.CommandDefinition;
import dk.sunepoulsen.adopt.core.registry.api.AbstractRegistryModule;
import dk.sunepoulsen.adopt.core.registry.api.RegistryException;

public class AnalyseCommandModule extends AbstractRegistryModule {
    @Override
    protected void configure() throws RegistryException {
        bind( CommandDefinition.class ).to( AnalyseCommandDefinition.class );
    }
}

/**
 * Module for the IT Worker application.
 */
module analysethis.cli.analyse {
    requires adopt.cli.command.api;
    requires adopt.core;
    requires analysethis.analyse.api;
    requires analysethis.persistence;
    requires commons.cli;
    requires slf4j.api;
    requires analysethis.cli.command.api;

    uses dk.sunepoulsen.analysethis.analyse.api.AnalyseModuleFactory;

    exports dk.sunepoulsen.analysethis.cli.command.analyse to adopt.core;
    provides dk.sunepoulsen.adopt.core.registry.api.RegistryModule with
        dk.sunepoulsen.analysethis.cli.command.analyse.AnalyseCommandModule;
}

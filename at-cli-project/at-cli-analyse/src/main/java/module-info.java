/**
 * Module for the IT Worker application.
 */
module analysethis.cli.analyse {
    requires adopt.cli.command.api;
    requires adopt.core;
    requires analysethis.persistence;
    requires commons.cli;
    requires slf4j.api;

    provides dk.sunepoulsen.adopt.cli.command.api.CommandDefinition with
        dk.sunepoulsen.analysethis.cli.command.analyse.AnalyseCommandDefinition;
}

/**
 * Module for the IT Worker application.
 */
module analysethis.cli.vcs {
    requires adopt.cli.command.api;
    requires analysethis.vcs.api;
    requires com.google.common;
    requires commons.cli;
    requires slf4j.api;
    requires analysethis.git;

    uses dk.sunepoulsen.analysethis.vcs.api.VCSClient;

    provides dk.sunepoulsen.adopt.cli.command.api.CommandDefinition with
        dk.sunepoulsen.analysethis.cli.command.vcs.ListReposCommandDefinition,
        dk.sunepoulsen.analysethis.cli.command.vcs.CloneReposCommandDefinition;
}

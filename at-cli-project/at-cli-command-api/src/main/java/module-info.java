module analysethis.cli.command.api {
    requires adopt.cli.command.api;
    requires adopt.core;
    requires analysethis.persistence;
    requires slf4j.api;

    exports dk.sunepoulsen.analysethis.cli.command.api;
}
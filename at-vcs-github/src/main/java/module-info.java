module analysethis.vcs.github {
    requires adopt.core;
    requires analysethis.vcs.api;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires slf4j.api;

    exports dk.sunepoulsen.analysethis.vcs.github to
        adopt.core;

    exports dk.sunepoulsen.analysethis.vcs.github.integration to
        com.fasterxml.jackson.databind,
        adopt.core;

    provides dk.sunepoulsen.adopt.core.environment.api.EnvironmentProvider with
        dk.sunepoulsen.analysethis.vcs.github.environment.EnvironmentGitHubProperties;

    opens dk.sunepoulsen.analysethis.vcs.github.modules to adopt.core;
    provides dk.sunepoulsen.adopt.core.registry.api.RegistryModule with
        dk.sunepoulsen.analysethis.vcs.github.modules.GitHubRegistryModule;
}

module analysethis.vcs.github {
    requires adopt.core;
    requires analysethis.vcs.api;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires slf4j.api;

    exports dk.sunepoulsen.analysethis.vcs.github.integration to com.fasterxml.jackson.databind;

    provides dk.sunepoulsen.adopt.core.environment.api.EnvironmentProvider with
        dk.sunepoulsen.analysethis.vcs.github.environment.EnvironmentGitHubProperties;

    provides dk.sunepoulsen.analysethis.vcs.api.VCSClient with
        dk.sunepoulsen.analysethis.vcs.github.VCSGitHubClient;
}

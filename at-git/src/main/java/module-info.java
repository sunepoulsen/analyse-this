module analysethis.git {
    requires adopt.core;
    requires com.google.common;
    requires org.apache.commons.io;
    requires slf4j.api;

    exports dk.sunepoulsen.analysethis.git;

    opens dk.sunepoulsen.analysethis.git.modules to adopt.core;
    provides dk.sunepoulsen.adopt.core.registry.api.RegistryModule with
        dk.sunepoulsen.analysethis.git.modules.GitRegistryModule;
}

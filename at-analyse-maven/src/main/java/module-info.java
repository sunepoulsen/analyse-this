module analysethis.analyse.maven {
    requires analysethis.analyse.api;
    requires slf4j.api;

    provides dk.sunepoulsen.analysethis.analyse.api.AnalyseModuleFactory with
        dk.sunepoulsen.analysethis.analyse.maven.AnalyseMavenModuleFactory;
}
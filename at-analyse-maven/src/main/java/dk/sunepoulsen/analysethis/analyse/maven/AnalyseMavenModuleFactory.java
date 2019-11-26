package dk.sunepoulsen.analysethis.analyse.maven;

import dk.sunepoulsen.analysethis.analyse.api.AnalyseModule;
import dk.sunepoulsen.analysethis.analyse.api.AnalyseModuleException;
import dk.sunepoulsen.analysethis.analyse.api.AnalyseModuleFactory;
import dk.sunepoulsen.analysethis.analyse.api.model.AnalyseModel;

public class AnalyseMavenModuleFactory implements AnalyseModuleFactory {
    @Override
    public AnalyseModule createAnalyseModule( AnalyseModel model ) throws AnalyseModuleException {
        return new AnalyseMavenModule( model );
    }
}

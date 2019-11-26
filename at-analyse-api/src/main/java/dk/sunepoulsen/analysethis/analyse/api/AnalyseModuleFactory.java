package dk.sunepoulsen.analysethis.analyse.api;

import dk.sunepoulsen.analysethis.analyse.api.model.AnalyseModel;

public interface AnalyseModuleFactory {
    AnalyseModule createAnalyseModule( AnalyseModel model ) throws AnalyseModuleException;
}

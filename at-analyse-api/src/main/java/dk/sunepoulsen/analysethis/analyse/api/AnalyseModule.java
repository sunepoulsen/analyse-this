package dk.sunepoulsen.analysethis.analyse.api;

import dk.sunepoulsen.analysethis.analyse.api.model.AnalyseModel;

public interface AnalyseModule {
    AnalyseModel analyse() throws AnalyseModuleException;
}

package dk.sunepoulsen.analysethis.analyse.maven;

import dk.sunepoulsen.analysethis.analyse.api.AnalyseModule;
import dk.sunepoulsen.analysethis.analyse.api.AnalyseModuleException;
import dk.sunepoulsen.analysethis.analyse.api.model.AnalyseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyseMavenModule implements AnalyseModule {
    private static Logger consoleLogger = LoggerFactory.getLogger( "adopt.cli.console.output.logger" );

    private AnalyseModel model;

    public AnalyseMavenModule( AnalyseModel model ) {
        this.model = model;
    }

    @Override
    public AnalyseModel analyse() throws AnalyseModuleException {
        consoleLogger.info( "Analysing maven structure for module {}/{}. Not implemented yet!", model.getRepositoryModel().getProject(), model.getRepositoryModel().getName() );

        return model;
    }

}

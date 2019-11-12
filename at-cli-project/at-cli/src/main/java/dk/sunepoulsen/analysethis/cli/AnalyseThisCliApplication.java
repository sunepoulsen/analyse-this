package dk.sunepoulsen.analysethis.cli;

import dk.sunepoulsen.adopt.cli.application.AdoptCliApplication;
import dk.sunepoulsen.adopt.core.environment.EnvironmentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyseThisCliApplication extends AdoptCliApplication {
    private static Logger log = LoggerFactory.getLogger( AnalyseThisCliApplication.class );
    private static Logger consoleLogger = LoggerFactory.getLogger( AdoptCliApplication.CONSOLE_LOGGER_NAME );

    public static void main(String[] args) {
        launchApplication( AnalyseThisCliApplication.class, args );
    }

    @Override
    public void start() {
        try {
            consoleLogger.info( "{} version {}", environment().getString( "application.name" ), environment().getString( "application.version" ) );
        }
        catch( EnvironmentException ex ) {
            consoleLogger.error( "Unable to read property: {}", ex.getMessage());
            log.error( "Unable to read property: {}", ex.getMessage(), ex);
        }
    }
}

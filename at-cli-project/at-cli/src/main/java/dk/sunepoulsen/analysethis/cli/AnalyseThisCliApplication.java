package dk.sunepoulsen.analysethis.cli;

import dk.sunepoulsen.adopt.cli.application.AdoptCliApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyseThisCliApplication extends AdoptCliApplication {
    private static Logger log = LoggerFactory.getLogger( AnalyseThisCliApplication.class );

    public static void main(String[] args) {
        launchApplication( AnalyseThisCliApplication.class, args );
    }

    @Override
    public void start() {
        System.out.println( "Analyse This Cli version 1.0.0" );
    }
}

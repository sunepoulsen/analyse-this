package dk.sunepoulsen.analysethis.analyse.api;

public class AnalyseModuleException extends Exception {
    public AnalyseModuleException( String message ) {
        super( message );
    }

    public AnalyseModuleException( String message, Throwable cause ) {
        super( message, cause );
    }
}

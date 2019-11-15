package dk.sunepoulsen.analysethis.git;

import dk.sunepoulsen.adopt.core.os.OperatingSystemFactory;
import dk.sunepoulsen.adopt.core.os.api.OperatingSystem;
import dk.sunepoulsen.adopt.core.os.api.OperatingSystemException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GitClient {
    private static Logger consoleLogger = LoggerFactory.getLogger( "adopt.cli.console.output.logger" );

    private static final String GIT_COMMAND = "git";
    private final String sourcesDir;

    public GitClient() throws GitClientException {
        try {
            OperatingSystem os = OperatingSystemFactory.getInstance();
            this.sourcesDir = os.applicationDataDirectory().getAbsolutePath() + "/sources";
        }
        catch( IOException | OperatingSystemException ex ) {
            throw new GitClientException( ex.getMessage(), ex );
        }
    }

    public void cloneRepo( String projectName, String repoName, String url ) throws IOException, InterruptedException {
        File projectDir = new File( sourcesDir + "/" + projectName );
        File repoDir = new File( sourcesDir + "/" + projectName + "/" + repoName );
        if( repoDir.exists() ) {
            consoleLogger.debug( "Deleting directory {}", repoDir.getAbsolutePath() );
            FileUtils.deleteDirectory( repoDir );

            if( repoDir.exists() ) {
                throw new IOException( "Unable to delete directory: " + repoDir.getAbsolutePath() );
            }
        }

        ProcessBuilder processBuilder = new ProcessBuilder( GIT_COMMAND, "clone", url );
        processBuilder.redirectErrorStream( true );
        processBuilder.directory( projectDir );

        if( !projectDir.exists() ) {
            projectDir.mkdirs();
        }
        Process process = processBuilder.start();
        process.waitFor();

        if( process.exitValue() != 0 ) {
            throw new IOException( IOUtils.toString( process.getInputStream(), StandardCharsets.UTF_8 ) );
        }
    }
}

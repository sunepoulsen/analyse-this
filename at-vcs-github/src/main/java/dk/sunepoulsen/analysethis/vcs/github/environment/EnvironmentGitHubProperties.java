package dk.sunepoulsen.analysethis.vcs.github.environment;

import dk.sunepoulsen.adopt.core.environment.api.EnvironmentProvider;
import dk.sunepoulsen.adopt.core.os.OperatingSystemFactory;
import dk.sunepoulsen.adopt.core.os.api.OperatingSystem;
import dk.sunepoulsen.adopt.core.os.api.OperatingSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class EnvironmentGitHubProperties implements EnvironmentProvider {
    private static Logger log = LoggerFactory.getLogger( EnvironmentGitHubProperties.class );

    public static final String GITHUB_ENABLED_KEY = "vcs.github.enabled";

    @Override
    public Map<String, Object> readEnvironment() {
        Map<String, Object> map = new HashMap<>();

        try {
            OperatingSystem os = OperatingSystemFactory.getInstance();
            File file = new File(os.applicationDataDirectory().getAbsolutePath() + "/github.properties" );

            if( !file.exists()) {
                map.put(GITHUB_ENABLED_KEY, false);
            }
            else {
                map = readFromFile( file );
                map.put(GITHUB_ENABLED_KEY, true);
            }
        }
        catch( OperatingSystemException | IOException ex ) {
            log.warn( "Unable to read GitHub properties", ex );
        }

        return map;
    }

    private Map<String, Object> readFromFile( File file ) {
        Map<String, Object> map = new HashMap<>();

        Properties properties = new Properties();
        try {
            properties.load( new FileInputStream( file ) );
            properties
                .forEach( ( key, value ) -> map.put( key.toString(), value ) );
        }
        catch( IOException ex ) {
            log.error( "Unable to read properties from file /" + file.getAbsolutePath(), ex );
        }

        return map;
    }
}

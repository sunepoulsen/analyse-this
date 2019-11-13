package dk.sunepoulsen.analysethis.vcs.github.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.sunepoulsen.analysethis.vcs.api.VCSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class GitHubIntegrator {
    private static Logger log = LoggerFactory.getLogger( GitHubIntegrator.class );

    private static final String AUTHORIZATION_HEADER_PATTERN = "token %s";
    private static final String ACCEPT_HEADER_VALUE = "application/vnd.github.mercy-preview+json";

    private String baseUrl;
    private int connectTimeout;
    private int requestTimeout;
    private String authorizationToken;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl( String baseUrl ) {
        this.baseUrl = baseUrl;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout( int connectTimeout ) {
        this.connectTimeout = connectTimeout;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout( int requestTimeout ) {
        this.requestTimeout = requestTimeout;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken( String authorizationToken ) {
        this.authorizationToken = authorizationToken;
    }

    public List<GitHubRepository> fetchRepositories() throws VCSException {
        return fetchRepositoriesFromPages( createHttpClient(), 1 );
    }

    private List<GitHubRepository> fetchRepositoriesFromPages( HttpClient httpClient, int pageNo ) throws VCSException {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri( URI.create( this.baseUrl + "/user/repos?page=" + pageNo ) )
                .timeout( Duration.ofSeconds( requestTimeout ) )
                .GET()
                .header( "Authorization", String.format( AUTHORIZATION_HEADER_PATTERN, authorizationToken ) )
                .header( "Accept", ACCEPT_HEADER_VALUE );

            HttpResponse<String> httpResponse = httpClient.send( requestBuilder.build(), HttpResponse.BodyHandlers.ofString() );
            if( httpResponse.statusCode() != 200 ) {
                GitHubError gitHubError = new ObjectMapper().readValue( httpResponse.body(), GitHubError.class );
                throw new VCSException( gitHubError.getMessage() );
            }

            List<GitHubRepository> repositories = new ObjectMapper().readValue( httpResponse.body(), new TypeReference<List<GitHubRepository>>() {
            } );
            httpResponse.headers().firstValue( "Link" ).ifPresent( s -> {
                if( s.contains( "next" ) ) {
                    try {
                        repositories.addAll( fetchRepositoriesFromPages( httpClient, pageNo + 1 ) );
                    }
                    catch( VCSException ex ) {
                        log.warn( "Unable to fetch GitHub repositories on page {}", pageNo, ex );
                    }
                }
            } );

            return repositories;
        }
        catch( InterruptedException | IOException ex ) {
            throw new VCSException( ex.getMessage(), ex );
        }
    }

    private HttpClient createHttpClient() {
        return HttpClient.newBuilder()
            .version( HttpClient.Version.HTTP_1_1 )
            .followRedirects( HttpClient.Redirect.NORMAL )
            .connectTimeout( Duration.ofSeconds( connectTimeout ) )
            .build();
    }
}

package dk.sunepoulsen.analysethis.vcs.github.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import dk.sunepoulsen.analysethis.vcs.api.VCSException;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class GitHubIntegratorTest {
    @Rule
    public WireMockRule githubServer = new WireMockRule(options().dynamicPort().usingFilesUnderClasspath( "target/test-classes/" + getClass().getPackageName().replace( ".", "/" ) ));

    @Test(expected = VCSException.class)
    public void testFetchRepositories_BadAuthentication() throws Exception {
        githubServer.stubFor( WireMock.get(WireMock.urlEqualTo("/user/repos?page=1"))
            .withHeader( "Authorization", WireMock.equalTo( "token token" ) )
            .withHeader( "Accept", WireMock.equalTo( "application/vnd.github.mercy-preview+json" ))
            .willReturn( WireMock.aResponse()
                .withStatus( 401 )
                .withHeader("Content-Type", "application/json; charset=utf-8")
                .withBodyFile( "github-bad-token.json" )
            )
        );

        GitHubIntegrator integrator = new GitHubIntegrator();
        integrator.setBaseUrl( "http://localhost:" + githubServer.port() );
        integrator.setConnectTimeout( 20 );
        integrator.setRequestTimeout( 120 );
        integrator.setAuthorizationToken( "token" );

        integrator.fetchRepositories();
    }

    @Test
    public void testFetchRepositories_EmptyResult() throws Exception {
        githubServer.stubFor( WireMock.get(WireMock.urlEqualTo("/user/repos?page=1"))
            .withHeader( "Authorization", WireMock.equalTo( "token token" ) )
            .withHeader( "Accept", WireMock.equalTo( "application/vnd.github.mercy-preview+json" ))
            .willReturn( WireMock.aResponse()
                .withStatus( 200 )
                .withHeader("Content-Type", "application/json; charset=utf-8")
                .withBodyFile( "github-empty-repositories.json" )
            )
        );

        GitHubIntegrator integrator = new GitHubIntegrator();
        integrator.setBaseUrl( "http://localhost:" + githubServer.port() );
        integrator.setConnectTimeout( 20 );
        integrator.setRequestTimeout( 120 );
        integrator.setAuthorizationToken( "token" );

        assertThat( integrator.fetchRepositories(), equalTo( Collections.emptyList() ));
    }

    @Test
    public void testFetchRepositories_SinglePageResult() throws Exception {
        githubServer.stubFor( WireMock.get(WireMock.urlEqualTo("/user/repos?page=1"))
            .withHeader( "Authorization", WireMock.equalTo( "token token" ) )
            .withHeader( "Accept", WireMock.equalTo( "application/vnd.github.mercy-preview+json" ))
            .willReturn( WireMock.aResponse()
                .withStatus( 200 )
                .withHeader("Content-Type", "application/json; charset=utf-8")
                .withHeader("Link", "")
                .withBodyFile( "github-repositories-page1.json" )
            )
        );

        GitHubIntegrator integrator = new GitHubIntegrator();
        integrator.setBaseUrl( "http://localhost:" + githubServer.port() );
        integrator.setConnectTimeout( 20 );
        integrator.setRequestTimeout( 120 );
        integrator.setAuthorizationToken( "token" );

        GitHubRepository repository = new GitHubRepository();
        repository.setName( "repo1" );
        repository.setDescription( "Repo1 description" );
        assertThat( integrator.fetchRepositories(), equalTo( Collections.singletonList( repository ) ));
    }

    @Test
    public void testFetchRepositories_MultiplePagesResult() throws Exception {
        githubServer.stubFor( WireMock.get(WireMock.urlEqualTo("/user/repos?page=1"))
            .withHeader( "Authorization", WireMock.equalTo( "token token" ) )
            .withHeader( "Accept", WireMock.equalTo( "application/vnd.github.mercy-preview+json" ))
            .willReturn( WireMock.aResponse()
                .withStatus( 200 )
                .withHeader("Content-Type", "application/json; charset=utf-8")
                .withHeader("Link", "<https://api.github.com/user/repos?page=2>; rel=\"next\", <https://api.github.com/user/repos?page=2>; rel=\"last\"")
                .withBodyFile( "github-repositories-page1.json" )
            )
        );

        githubServer.stubFor( WireMock.get(WireMock.urlEqualTo("/user/repos?page=2"))
            .withHeader( "Authorization", WireMock.equalTo( "token token" ) )
            .withHeader( "Accept", WireMock.equalTo( "application/vnd.github.mercy-preview+json" ))
            .willReturn( WireMock.aResponse()
                .withStatus( 200 )
                .withHeader("Content-Type", "application/json; charset=utf-8")
                .withHeader("Link", "<https://api.github.com/user/repos?page=1>; rel=\"first\", <https://api.github.com/user/repos?page=1>; rel=\"prev\"")
                .withBodyFile( "github-repositories-page2.json" )
            )
        );

        GitHubIntegrator integrator = new GitHubIntegrator();
        integrator.setBaseUrl( "http://localhost:" + githubServer.port() );
        integrator.setConnectTimeout( 20 );
        integrator.setRequestTimeout( 120 );
        integrator.setAuthorizationToken( "token" );

        GitHubRepository repository1 = new GitHubRepository();
        repository1.setName( "repo1" );
        repository1.setDescription( "Repo1 description" );

        GitHubRepository repository2 = new GitHubRepository();
        repository2.setName( "repo2" );
        repository2.setDescription( "Repo2 description" );

        assertThat( integrator.fetchRepositories(), equalTo( Arrays.asList( repository1, repository2 ) ));
    }
}

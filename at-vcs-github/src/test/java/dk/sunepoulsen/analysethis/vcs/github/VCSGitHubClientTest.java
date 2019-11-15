package dk.sunepoulsen.analysethis.vcs.github;

import dk.sunepoulsen.adopt.core.environment.Environment;
import dk.sunepoulsen.adopt.core.environment.EnvironmentException;
import dk.sunepoulsen.analysethis.vcs.api.VCSException;
import dk.sunepoulsen.analysethis.vcs.api.VCSRepository;
import dk.sunepoulsen.analysethis.vcs.github.environment.EnvironmentGitHubProperties;
import dk.sunepoulsen.analysethis.vcs.github.integration.GitHubIntegrator;
import dk.sunepoulsen.analysethis.vcs.github.integration.GitHubRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.class )
public class VCSGitHubClientTest {
    @Mock
    private Environment environment;

    @Mock
    private GitHubIntegrator integrator;

    private VCSGitHubClient client;

    @Before
    public void setupInstances() throws Exception {
        when(environment.getString( eq("vcs.github.url" ))).thenReturn( "http://localhost" );
        when(environment.getString( eq("vcs.connect.timeout" ))).thenReturn( "10" );
        when(environment.getString( eq("vcs.request.timeout" ))).thenReturn( "10" );
        when(environment.getString( eq("vcs.github.token" ))).thenReturn( "token" );

        this.client = new VCSGitHubClient( environment, integrator );
    }

    @Test(expected = VCSException.class )
    public void testEnabled_EnvironmentThrowsException() throws Exception {
        when(environment.getBoolean( eq( EnvironmentGitHubProperties.GITHUB_ENABLED_KEY), eq(false) )).thenThrow( new EnvironmentException("message") );
        client.enabled();
    }

    @Test
    public void testEnabled_EnvironmentKeyIsTrue() throws Exception {
        when(environment.getBoolean( eq( EnvironmentGitHubProperties.GITHUB_ENABLED_KEY), eq(false) )).thenReturn( true );

        assertThat( client.enabled(), equalTo(true));
    }

    @Test
    public void testFetchRepositories_NoFullName() throws Exception {
        GitHubRepository gitHubRepository = new GitHubRepository();
        gitHubRepository.setName( "repo1" );
        gitHubRepository.setDescription( "Repo1 description" );
        gitHubRepository.setFullName( null );

        when(integrator.fetchRepositories()).thenReturn( Collections.singletonList(gitHubRepository) );
        List<VCSRepository> actual = client.fetchRepositories();

        VCSRepository expected = new VCSRepository();
        expected.setName( "repo1" );
        expected.setDescription( "Repo1 description" );
        expected.setProjectName( null );

        assertThat(actual, equalTo(Collections.singletonList( expected )));
    }

    @Test(expected = VCSException.class)
    public void testFetchRepositories_FullNameIsEmpty() throws Exception {
        GitHubRepository gitHubRepository = new GitHubRepository();
        gitHubRepository.setName( "repo1" );
        gitHubRepository.setDescription( "Repo1 description" );
        gitHubRepository.setFullName( "" );

        when(integrator.fetchRepositories()).thenReturn( Collections.singletonList(gitHubRepository) );
        client.fetchRepositories();
    }

    @Test(expected = VCSException.class)
    public void testFetchRepositories_WrongFormat() throws Exception {
        GitHubRepository gitHubRepository = new GitHubRepository();
        gitHubRepository.setName( "repo1" );
        gitHubRepository.setDescription( "Repo1 description" );
        gitHubRepository.setFullName( "Wrong Format" );

        when(integrator.fetchRepositories()).thenReturn( Collections.singletonList(gitHubRepository) );
        client.fetchRepositories();
    }

    @Test
    public void testFetchRepositories_CorrectFormat() throws Exception {
        GitHubRepository gitHubRepository = new GitHubRepository();
        gitHubRepository.setName( "repo1" );
        gitHubRepository.setDescription( "Repo1 description" );
        gitHubRepository.setFullName( "project/repository" );

        when(integrator.fetchRepositories()).thenReturn( Collections.singletonList(gitHubRepository) );
        List<VCSRepository> actual = client.fetchRepositories();

        VCSRepository expected = new VCSRepository();
        expected.setName( "repo1" );
        expected.setDescription( "Repo1 description" );
        expected.setProjectName( "project" );

        assertThat(actual, equalTo(Collections.singletonList( expected )));
    }
}

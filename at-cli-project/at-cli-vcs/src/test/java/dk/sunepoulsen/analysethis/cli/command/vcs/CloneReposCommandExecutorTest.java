package dk.sunepoulsen.analysethis.cli.command.vcs;

import dk.sunepoulsen.adopt.cli.command.api.CliException;
import dk.sunepoulsen.analysethis.git.GitClient;
import dk.sunepoulsen.analysethis.vcs.api.VCSClient;
import dk.sunepoulsen.analysethis.vcs.api.VCSException;
import dk.sunepoulsen.analysethis.vcs.api.VCSRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith( MockitoJUnitRunner.class )
public class CloneReposCommandExecutorTest {
    @Mock
    private VCSRegistry vcsRegistry;

    @Mock
    private GitClient gitClient;

    @Mock
    public VCSClient vcsGitClient;

    private List<VCSRepository> vcsRepositories;
    private List<VCSClient> vcsClients;

    @Before
    public void setupInstances() {
        this.vcsClients = Collections.singletonList( vcsGitClient );
        this.vcsRepositories = new ArrayList<>();

        VCSRepository vcsRepository1 = new VCSRepository();
        vcsRepository1.setName( "repo1" );
        vcsRepository1.setDescription( "Repo1 description" );
        vcsRepository1.setProjectName( "project" );
        vcsRepository1.setCloneUrl( "url1" );
        this.vcsRepositories.add(vcsRepository1);

        VCSRepository vcsRepository2 = new VCSRepository();
        vcsRepository2.setName( "repo2" );
        vcsRepository2.setDescription( "Repo2 description" );
        vcsRepository2.setProjectName( "project" );
        vcsRepository2.setCloneUrl( "url2" );
        this.vcsRepositories.add(vcsRepository2);
    }

    @Test
    public void testPerformAction_CloneRepositories_VCSClient_EnabledThrowsException() throws Exception {
        when(vcsRegistry.stream()).thenReturn( this.vcsClients.stream() );

        when(vcsGitClient.enabled()).thenThrow( new VCSException( "message" ) );
        when(vcsGitClient.fetchRepositories()).thenReturn( vcsRepositories );

        CloneReposCommandExecutor executor = new CloneReposCommandExecutor( vcsRegistry, null, gitClient );
        executor.performAction();

        verify(gitClient, never()).cloneRepo( anyString(), anyString(), anyString() );
    }

    @Test( expected = CliException.class )
    public void testPerformAction_CloneRepositories_VCSClient_FetchRepositoriesThrowsException() throws Exception {
        when(vcsRegistry.stream()).thenReturn( this.vcsClients.stream() );

        when(vcsGitClient.enabled()).thenReturn( true );
        when(vcsGitClient.fetchRepositories()).thenThrow( new VCSException( "message" ) );

        CloneReposCommandExecutor executor = new CloneReposCommandExecutor( vcsRegistry, null, gitClient );
        executor.performAction();
    }

    @Test
    public void testPerformAction_CloneRepositories_RepoNamesIsNull() throws Exception {
        when(vcsRegistry.stream()).thenReturn( this.vcsClients.stream() );

        when(vcsGitClient.enabled()).thenReturn( true );
        when(vcsGitClient.fetchRepositories()).thenReturn( this.vcsRepositories);

        CloneReposCommandExecutor executor = new CloneReposCommandExecutor( vcsRegistry, null, gitClient );
        executor.performAction();

        InOrder inOrder = Mockito.inOrder( gitClient );

        inOrder.verify(gitClient).cloneRepo( eq("project"), eq("repo1"), eq("url1") );
        inOrder.verify(gitClient).cloneRepo( eq("project"), eq("repo2"), eq("url2") );
    }

    @Test
    public void testPerformAction_CloneRepositories_RepoNamesIsEmpty() throws Exception {
        when(vcsRegistry.stream()).thenReturn( this.vcsClients.stream() );

        when(vcsGitClient.enabled()).thenReturn( true );
        when(vcsGitClient.fetchRepositories()).thenReturn( this.vcsRepositories);

        CloneReposCommandExecutor executor = new CloneReposCommandExecutor( vcsRegistry, new ArrayList<>(), gitClient );
        executor.performAction();

        InOrder inOrder = Mockito.inOrder( gitClient );

        inOrder.verify(gitClient).cloneRepo( eq("project"), eq("repo1"), eq("url1") );
        inOrder.verify(gitClient).cloneRepo( eq("project"), eq("repo2"), eq("url2") );
    }

    @Test
    public void testPerformAction_CloneRepositories_OneRepoMatchFilter() throws Exception {
        when(vcsRegistry.stream()).thenReturn( this.vcsClients.stream() );

        when(vcsGitClient.enabled()).thenReturn( true );
        when(vcsGitClient.fetchRepositories()).thenReturn( this.vcsRepositories);

        CloneReposCommandExecutor executor = new CloneReposCommandExecutor( vcsRegistry, Collections.singletonList( "repo1" ), gitClient );
        executor.performAction();

        InOrder inOrder = Mockito.inOrder( gitClient );

        inOrder.verify(gitClient).cloneRepo( eq("project"), eq("repo1"), eq("url1") );
    }

    @Test
    public void testPerformAction_CloneRepositories_NoRepoMatchFilter() throws Exception {
        when(vcsRegistry.stream()).thenReturn( this.vcsClients.stream() );

        when(vcsGitClient.enabled()).thenReturn( true );
        when(vcsGitClient.fetchRepositories()).thenReturn( vcsRepositories );

        CloneReposCommandExecutor executor = new CloneReposCommandExecutor( vcsRegistry, Collections.singletonList( "never-found" ), gitClient );
        executor.performAction();

        verify(gitClient, never()).cloneRepo( anyString(), anyString(), anyString() );
    }
}

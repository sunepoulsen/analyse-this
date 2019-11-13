package dk.sunepoulsen.analysethis.vcs.api;

import java.util.List;

public interface VCSClient {
    boolean enabled() throws VCSException;
    List<VCSRepository> fetchRepositories() throws VCSException;
}

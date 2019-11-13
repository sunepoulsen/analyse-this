package dk.sunepoulsen.analysethis.cli.command.vcs;

import com.google.common.collect.Lists;
import dk.sunepoulsen.analysethis.vcs.api.VCSClient;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public class VCSRegistry {
    private List<VCSClient> vcsClients;

    public VCSRegistry() {
        this.vcsClients = loadVCSClientsFromProviders();
    }

    public Stream<VCSClient> stream() {
        return vcsClients.stream();
    }

    private List<VCSClient> loadVCSClientsFromProviders() {
        ServiceLoader<VCSClient> loader = ServiceLoader.load( VCSClient.class );

        return Lists.newArrayList( loader.iterator() );
    }
}

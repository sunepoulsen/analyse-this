package dk.sunepoulsen.analysethis.analyse.api.model;

import java.util.Objects;

public class AnalyseModel {
    private AnalyseRepositoryModel repositoryModel;

    public AnalyseRepositoryModel getRepositoryModel() {
        return repositoryModel;
    }

    public void setRepositoryModel( AnalyseRepositoryModel repositoryModel ) {
        this.repositoryModel = repositoryModel;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) {
            return true;
        }
        if( !( o instanceof AnalyseModel ) ) {
            return false;
        }
        AnalyseModel that = ( AnalyseModel ) o;
        return Objects.equals( repositoryModel, that.repositoryModel );
    }

    @Override
    public int hashCode() {
        return Objects.hash( repositoryModel );
    }

    @Override
    public String toString() {
        return "AnalyseModel{" +
            "repositoryModel=" + repositoryModel +
            '}';
    }
}

package dk.sunepoulsen.analysethis.analyse.api.model;

import java.util.Objects;

public class AnalyseRepositoryModel {
    private String vcs;
    private String project;
    private String name;
    private String description;
    private String cloneUrl;

    public String getVcs() {
        return vcs;
    }

    public void setVcs( String vcs ) {
        this.vcs = vcs;
    }

    public String getProject() {
        return project;
    }

    public void setProject( String project ) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public void setCloneUrl( String cloneUrl ) {
        this.cloneUrl = cloneUrl;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) {
            return true;
        }
        if( !( o instanceof AnalyseRepositoryModel ) ) {
            return false;
        }
        AnalyseRepositoryModel that = ( AnalyseRepositoryModel ) o;
        return Objects.equals( vcs, that.vcs ) &&
            Objects.equals( project, that.project ) &&
            Objects.equals( name, that.name ) &&
            Objects.equals( description, that.description ) &&
            Objects.equals( cloneUrl, that.cloneUrl );
    }

    @Override
    public int hashCode() {
        return Objects.hash( vcs, project, name, description, cloneUrl );
    }

    @Override
    public String toString() {
        return "AnalyseRepositoryModel{" +
            "vcs='" + vcs + '\'' +
            ", project='" + project + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", cloneUrl='" + cloneUrl + '\'' +
            '}';
    }
}

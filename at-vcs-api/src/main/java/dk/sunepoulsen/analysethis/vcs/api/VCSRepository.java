package dk.sunepoulsen.analysethis.vcs.api;

import java.util.Objects;

public class VCSRepository {
    private String name;
    private String description;
    private String projectName;
    private String cloneUrl;

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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName( String projectName ) {
        this.projectName = projectName;
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
        if( !( o instanceof VCSRepository ) ) {
            return false;
        }
        VCSRepository that = ( VCSRepository ) o;
        return Objects.equals( name, that.name ) &&
            Objects.equals( description, that.description ) &&
            Objects.equals( projectName, that.projectName ) &&
            Objects.equals( cloneUrl, that.cloneUrl );
    }

    @Override
    public int hashCode() {
        return Objects.hash( name, description, projectName, cloneUrl );
    }

    @Override
    public String toString() {
        return "VCSRepository{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", projectName='" + projectName + '\'' +
            ", cloneUrl='" + cloneUrl + '\'' +
            '}';
    }
}

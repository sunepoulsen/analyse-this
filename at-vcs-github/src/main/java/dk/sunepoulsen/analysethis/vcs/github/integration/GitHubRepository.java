package dk.sunepoulsen.analysethis.vcs.github.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Objects;

@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonNaming( PropertyNamingStrategy.SnakeCaseStrategy.class )
@JsonIgnoreProperties( ignoreUnknown = true )
public class GitHubRepository {
    private String name;
    private String description;

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

    @Override
    public boolean equals( Object o ) {
        if( this == o ) {
            return true;
        }
        if( !( o instanceof GitHubRepository ) ) {
            return false;
        }
        GitHubRepository that = ( GitHubRepository ) o;
        return Objects.equals( name, that.name ) &&
            Objects.equals( description, that.description );
    }

    @Override
    public int hashCode() {
        return Objects.hash( name, description );
    }

    @Override
    public String toString() {
        return "GitHubRepository{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}

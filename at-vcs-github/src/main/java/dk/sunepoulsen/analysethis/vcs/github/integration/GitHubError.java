package dk.sunepoulsen.analysethis.vcs.github.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Objects;

@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonNaming( PropertyNamingStrategy.SnakeCaseStrategy.class )
@JsonIgnoreProperties( ignoreUnknown = true )
public class GitHubError {
    private String message;
    private String documentationUrl;

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl( String documentationUrl ) {
        this.documentationUrl = documentationUrl;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) {
            return true;
        }
        if( !( o instanceof GitHubError ) ) {
            return false;
        }
        GitHubError that = ( GitHubError ) o;
        return Objects.equals( message, that.message ) &&
            Objects.equals( documentationUrl, that.documentationUrl );
    }

    @Override
    public int hashCode() {
        return Objects.hash( message, documentationUrl );
    }

    @Override
    public String toString() {
        return "GitHubError{" +
            "message='" + message + '\'' +
            ", documentationUrl='" + documentationUrl + '\'' +
            '}';
    }
}

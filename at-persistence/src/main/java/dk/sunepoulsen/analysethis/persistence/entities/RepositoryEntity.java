package dk.sunepoulsen.analysethis.persistence.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table( name = "repositories" )
@NamedQueries( {
    @NamedQuery( name = "findRepository", query = "SELECT repo FROM RepositoryEntity repo WHERE repo.project = :project AND repo.name = :name")
} )
public class RepositoryEntity {
    /**
     * Primary key.
     */
    @Id
    @SequenceGenerator( name = "repository_id_seq_generator", sequenceName = "repository_id_seq", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "repository_id_seq_generator" )
    @Column( name = "repository_id" )
    private Long id;

    @Column( name = "vcs", nullable = false )
    private String vcs;

    @Column( name = "project", nullable = false )
    private String project;

    @Column( name = "name", nullable = false )
    private String name;

    @Lob
    @Column( name = "description" )
    private String description;

    @Column( name = "clone_url", nullable = false )
    private String cloneUrl;

    @Column( name = "analysed_at" )
    private LocalDateTime analysedAt;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

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

    public LocalDateTime getAnalysedAt() {
        return analysedAt;
    }

    public void setAnalysedAt( LocalDateTime analysedAt ) {
        this.analysedAt = analysedAt;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) {
            return true;
        }
        if( !( o instanceof RepositoryEntity ) ) {
            return false;
        }
        RepositoryEntity that = ( RepositoryEntity ) o;
        return Objects.equals( id, that.id ) &&
            Objects.equals( vcs, that.vcs ) &&
            Objects.equals( project, that.project ) &&
            Objects.equals( name, that.name ) &&
            Objects.equals( description, that.description ) &&
            Objects.equals( cloneUrl, that.cloneUrl ) &&
            Objects.equals( analysedAt, that.analysedAt );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, vcs, project, name, description, cloneUrl, analysedAt );
    }

    @Override
    public String toString() {
        return "RepositoryEntity{" +
            "id=" + id +
            ", vcs='" + vcs + '\'' +
            ", project='" + project + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", cloneUrl='" + cloneUrl + '\'' +
            ", analysedAt=" + analysedAt +
            '}';
    }
}

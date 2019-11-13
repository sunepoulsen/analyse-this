package dk.sunepoulsen.analysethis.vcs.api;

public class VCSRepository {
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
}

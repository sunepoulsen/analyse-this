module analysethis.persistence {
    requires adopt.core;
    requires adopt.cli.application;

    requires java.persistence;
    requires java.sql;
    requires liquibase.core;

    requires slf4j.api;

    exports dk.sunepoulsen.analysethis.persistence;
    exports dk.sunepoulsen.analysethis.persistence.entities;
    exports dk.sunepoulsen.analysethis.persistence.services;

    opens db.changelog;
    opens dk.sunepoulsen.analysethis.persistence.entities to org.hibernate.orm.core;
}

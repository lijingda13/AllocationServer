package com.project.allocation.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

/**
 * Base entity class that provides common fields and functionality to all entity classes.
 * This class includes a unique identifier field for entity objects and implements Serializable for
 * Java serialization/deserialization.
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    /**
     * Serial version UID for the Serializable class, ensuring class compatibility during serialization and deserialization processes.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for each entity instance. This ID is automatically generated for each entity object
     * when it is persisted to the database. The strategy used for ID generation is IDENTITY, meaning that
     * the persistence provider will delegate the generation of the primary key to the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

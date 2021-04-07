package com.epam.esm.entity;

import java.math.BigInteger;
import java.util.Objects;

/**
 * @author Andrey Belik
 * @version 1.0
 * @see Entity
 */
public class Tag extends Entity<BigInteger> {

    private String name;

    /**
     * Constructor
     *
     * @param name name of the tag
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * Default constructor
     */
    public Tag() {
    }

    /**
     * Constructor
     *
     * @param id   id of the tag
     * @param name name of the tag
     */
    public Tag(BigInteger id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * Name getter
     *
     * @return name of the tag
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter
     *
     * @param name name of the tag
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                '}';
    }
}

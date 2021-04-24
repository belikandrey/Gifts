package com.epam.esm.exception;

public class EntityUsedException extends RuntimeException {
  public EntityUsedException() {}

  public EntityUsedException(String message, Class<?> entityClass) {
    super(message);
    this.entityClass = entityClass;
  }

  private Class<?> entityClass;

  public Class<?> getEntityClass() {
    return entityClass;
  }
}

package com.epam.esm.exception;

public class EntityDisabledException extends RuntimeException {

  private Class<?> entityClass;

  public Class<?> getEntityClass() {
    return entityClass;
  }

  public EntityDisabledException(String message, Class<?> entityClass) {
    super(message);
    this.entityClass = entityClass;
  }
}

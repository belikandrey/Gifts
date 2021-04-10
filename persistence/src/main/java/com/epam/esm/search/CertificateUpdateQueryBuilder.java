package com.epam.esm.search;

import java.math.BigDecimal;

public class CertificateUpdateQueryBuilder {
  private static final String SQL_BASE_UPDATE = "UPDATE gifts.certificate SET ";
  private static final String SQL_SET_NAME = "name = ";
  private static final String SQL_SEPARATOR = ", ";
  private static final String SQL_SET_DESCRIPTION = "description = ";
  private static final String SQL_SET_PRICE = "price = ";
  private static final String SQL_SET_DURATION = "duration = ";
  private static final String SQL_SET_LAST_UPDATE_DATE = "last_update_date = ";
  private boolean isComposite;
  private StringBuilder stringBuilder;

  public CertificateUpdateQueryBuilder() {
    stringBuilder = new StringBuilder(SQL_BASE_UPDATE);
  }

  public String build() {
    return stringBuilder.toString();
  }

  public CertificateUpdateQueryBuilder setName(String name){
    if(isComposite){
      stringBuilder.append(SQL_SEPARATOR);
    }
    stringBuilder.append(SQL_SET_NAME);
    stringBuilder.append(name);
    return this;
  }

  public CertificateUpdateQueryBuilder setDescription(String description){
    if(isComposite){
      stringBuilder.append(SQL_SEPARATOR);
    }
    stringBuilder.append(SQL_SET_DESCRIPTION);
    stringBuilder.append(description);
    return this;
  }

  public CertificateUpdateQueryBuilder setPrice(BigDecimal price){
    if(isComposite){
      stringBuilder.append(SQL_SEPARATOR);
    }
    stringBuilder.append(SQL_SET_PRICE);
    stringBuilder.append(price);
    return this;
  }

  public CertificateUpdateQueryBuilder setDuration(int duration){
    if(isComposite){

    }
    return this;
  }


}

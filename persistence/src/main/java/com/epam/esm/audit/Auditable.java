package com.epam.esm.audit;

import java.time.LocalDateTime;

public interface Auditable {
  LocalDateTime getLastUpdateDate();

  void setLastUpdateDate(LocalDateTime lastUpdateDate);

  LocalDateTime getCreateDate();

  void setCreateDate(LocalDateTime createDate);

  String getOperation();

  void setOperation(String operation);
}

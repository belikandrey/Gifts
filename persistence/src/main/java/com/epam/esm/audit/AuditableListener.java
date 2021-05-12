package com.epam.esm.audit;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditableListener {
    @PrePersist
    void preCreate(Auditable auditable){
        LocalDateTime now = LocalDateTime.now();
        auditable.setLastUpdateDate(now);
        auditable.setOperation("Create");
    }

    @PreUpdate
    void preUpdate(Auditable auditable){
        LocalDateTime now = LocalDateTime.now();
        auditable.setLastUpdateDate(now);
        auditable.setOperation("Update");
    }

    @PreRemove
    void preRemove(Auditable auditable){
        LocalDateTime now = LocalDateTime.now();
        auditable.setLastUpdateDate(now);
        auditable.setOperation("Remove");
    }
}

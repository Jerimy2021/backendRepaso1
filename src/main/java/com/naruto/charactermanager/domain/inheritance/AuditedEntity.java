package com.naruto.charactermanager.domain.inheritance;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.LastModifiedDate;
import com.naruto.charactermanager.domain.utils.AuditUtils;

import java.sql.Timestamp;

@MappedSuperclass
public class AuditedEntity extends CreationAuditedEntity {

    @LastModifiedDate
    @Comment("Fecha y hora de la última modificación")
    @Column(name = "FE_MODI_AUDI", columnDefinition = "TIMESTAMP")
    private Timestamp lastModificationTime;

    @Comment("ID del usuario que modificó el registro")
    @Column(name = "US_MODI_AUDI", length = 50)
    private String lastModifierUserId;

    public Timestamp getLastModificationTime() {
        return lastModificationTime;
    }

    private void setLastModificationTime(Timestamp lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public String getLastModifierUserId() {
        return lastModifierUserId;
    }

    private void setLastModifierUserId(String lastModifierUserId) {
        this.lastModifierUserId = lastModifierUserId;
    }

    public void modify(String userId) {
        setLastModificationTime(AuditUtils.now());
        setLastModifierUserId(AuditUtils.getValidUserId(userId));
    }
}


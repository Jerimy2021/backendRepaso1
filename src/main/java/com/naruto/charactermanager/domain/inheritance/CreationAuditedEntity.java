package com.naruto.charactermanager.domain.inheritance;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import com.naruto.charactermanager.domain.utils.AuditUtils;

import java.sql.Timestamp;

@MappedSuperclass
public class CreationAuditedEntity {
    
    @CreatedDate
    @Comment("Fecha y hora de la creación del registro")
    @Column(name = "FE_CREA_AUDI", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp creationTime;

    @CreatedBy
    @Comment("ID del usuario que creó el registro")
    @Column(name = "US_CREA_AUDI", length = 50, updatable = false)
    private String creatorUserId;

    public Timestamp getCreationTime() {
        return creationTime;
    }

    protected void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    private void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public void create(String userId) {
        setCreationTime(AuditUtils.now());
        setCreatorUserId(AuditUtils.getValidUserId(userId));
    }
}


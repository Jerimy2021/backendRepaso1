package com.naruto.charactermanager.domain.inheritance;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.Comment;
import com.naruto.charactermanager.domain.utils.AuditUtils;

import java.sql.Timestamp;

@MappedSuperclass
public class FullAuditedEntity extends AuditedEntity {

    @Comment("Estado de eliminación (0 = No, 1 = Sí)")
    @Column(name = "ES_ELIMINADO", nullable = false)
    private Boolean isDeleted;

    @Comment("Fecha y hora de eliminación")
    @Column(name = "FE_ELIM_AUDI", columnDefinition = "TIMESTAMP")
    private Timestamp deletionTime;

    @Comment("ID del usuario que eliminó el registro")
    @Column(name = "US_ELIM_AUDI", length = 50)
    private String deleterUserId;

    public Boolean getDeleted() {
        return isDeleted;
    }

    public FullAuditedEntity setDeleted(Boolean deleted) {
        isDeleted = deleted;
        return this;
    }

    public Timestamp getDeletionTime() {
        return deletionTime;
    }

    private void setDeletionTime(Timestamp deletionTime) {
        this.deletionTime = deletionTime;
    }

    private void resetDeletionTime() {
        this.deletionTime = null;
    }

    public String getDeleterUserId() {
        return deleterUserId;
    }

    public void setDeleterUserId(String deleterUserId) {
        this.deleterUserId = deleterUserId;
    }

    public void delete(String userId) {
        setDeleted(Boolean.TRUE);
        setDeletionTime(AuditUtils.now());
        setDeleterUserId(AuditUtils.getValidUserId(userId));
    }

    public void unDelete() {
        setDeleted(Boolean.FALSE);
        resetDeletionTime();
        setDeleterUserId(null);
    }

    @Override
    public void create(String userId) {
        setDeleted(Boolean.FALSE);
        super.create(userId);
    }
}


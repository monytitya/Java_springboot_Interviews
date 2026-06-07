package Springinterview.interviews.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity {

    @Version
    private Long version;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getVersion() {
        return version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    private void onPrePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    private void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PreRemove
    private void onPreRemove() {
        this.updatedAt = LocalDateTime.now();
    }

    @PostLoad
    private void onPostLoad() {
        System.out.println(getClass().getSimpleName() + " loaded: " + this);
    }

    @PostPersist
    private void onPostPersist() {
        System.out.println(getClass().getSimpleName() + " persisted: " + this);
    }

    @PostUpdate
    private void onPostUpdate() {
        System.out.println(getClass().getSimpleName() + " updated: " + this);
    }

    @PostRemove
    private void onPostRemove() {
        System.out.println(getClass().getSimpleName() + " removed: " + this);
    }
}

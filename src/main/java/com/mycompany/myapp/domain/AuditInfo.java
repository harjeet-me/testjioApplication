package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AuditInfo.
 */
@Entity
@Table(name = "audit_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuditInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToOne(mappedBy = "auditInfo")
    @JsonIgnore
    private FileSystem fileSystem;

    @OneToOne(mappedBy = "auditInfo")
    @JsonIgnore
    private Envelope envelope;

    @OneToOne(mappedBy = "auditInfo")
    @JsonIgnore
    private Conversation conversation;

    @OneToOne(mappedBy = "auditInfo")
    @JsonIgnore
    private DynamicDataEnvelope dynamicDataEnvelope;

    @OneToOne(mappedBy = "auditInfo")
    @JsonIgnore
    private DynamicData dynamicData;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public AuditInfo createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public AuditInfo createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public AuditInfo lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public AuditInfo lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public AuditInfo fileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        return this;
    }

    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public Envelope getEnvelope() {
        return envelope;
    }

    public AuditInfo envelope(Envelope envelope) {
        this.envelope = envelope;
        return this;
    }

    public void setEnvelope(Envelope envelope) {
        this.envelope = envelope;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public AuditInfo conversation(Conversation conversation) {
        this.conversation = conversation;
        return this;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public DynamicDataEnvelope getDynamicDataEnvelope() {
        return dynamicDataEnvelope;
    }

    public AuditInfo dynamicDataEnvelope(DynamicDataEnvelope dynamicDataEnvelope) {
        this.dynamicDataEnvelope = dynamicDataEnvelope;
        return this;
    }

    public void setDynamicDataEnvelope(DynamicDataEnvelope dynamicDataEnvelope) {
        this.dynamicDataEnvelope = dynamicDataEnvelope;
    }

    public DynamicData getDynamicData() {
        return dynamicData;
    }

    public AuditInfo dynamicData(DynamicData dynamicData) {
        this.dynamicData = dynamicData;
        return this;
    }

    public void setDynamicData(DynamicData dynamicData) {
        this.dynamicData = dynamicData;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditInfo)) {
            return false;
        }
        return id != null && id.equals(((AuditInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditInfo{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}

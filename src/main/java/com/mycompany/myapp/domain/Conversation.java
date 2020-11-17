package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.mycompany.myapp.domain.enumeration.CONVERSATIONTYPE;

/**
 * A Conversation.
 */
@Entity
@Table(name = "conversation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CONVERSATIONTYPE type;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;

    @Column(name = "attachment_content_type")
    private String attachmentContentType;

    @Column(name = "attachment_name")
    private String attachmentName;

    @Column(name = "status")
    private String status;

    @Column(name = "sent_date_time")
    private Instant sentDateTime;

    @OneToOne
    @JoinColumn(unique = true)
    private DynamicDataEnvelope dynamicDataEnvelope;

    @OneToOne
    @JoinColumn(unique = true)
    private AuditInfo auditInfo;

    @ManyToOne
    @JsonIgnoreProperties(value = "conversations", allowSetters = true)
    private Discrim discrim;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public Conversation subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public CONVERSATIONTYPE getType() {
        return type;
    }

    public Conversation type(CONVERSATIONTYPE type) {
        this.type = type;
        return this;
    }

    public void setType(CONVERSATIONTYPE type) {
        this.type = type;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public Conversation attachment(byte[] attachment) {
        this.attachment = attachment;
        return this;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public Conversation attachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
        return this;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public Conversation attachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
        return this;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getStatus() {
        return status;
    }

    public Conversation status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getSentDateTime() {
        return sentDateTime;
    }

    public Conversation sentDateTime(Instant sentDateTime) {
        this.sentDateTime = sentDateTime;
        return this;
    }

    public void setSentDateTime(Instant sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    public DynamicDataEnvelope getDynamicDataEnvelope() {
        return dynamicDataEnvelope;
    }

    public Conversation dynamicDataEnvelope(DynamicDataEnvelope dynamicDataEnvelope) {
        this.dynamicDataEnvelope = dynamicDataEnvelope;
        return this;
    }

    public void setDynamicDataEnvelope(DynamicDataEnvelope dynamicDataEnvelope) {
        this.dynamicDataEnvelope = dynamicDataEnvelope;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public Conversation auditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
        return this;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    public Discrim getDiscrim() {
        return discrim;
    }

    public Conversation discrim(Discrim discrim) {
        this.discrim = discrim;
        return this;
    }

    public void setDiscrim(Discrim discrim) {
        this.discrim = discrim;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conversation)) {
            return false;
        }
        return id != null && id.equals(((Conversation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Conversation{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", type='" + getType() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", attachmentContentType='" + getAttachmentContentType() + "'" +
            ", attachmentName='" + getAttachmentName() + "'" +
            ", status='" + getStatus() + "'" +
            ", sentDateTime='" + getSentDateTime() + "'" +
            "}";
    }
}

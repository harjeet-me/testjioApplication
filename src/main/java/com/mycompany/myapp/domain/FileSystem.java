package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A FileSystem.
 */
@Entity
@Table(name = "file_system")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FileSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "data")
    private byte[] data;

    @Column(name = "data_content_type")
    private String dataContentType;

    @Column(name = "ext")
    private Boolean ext;

    @Column(name = "url")
    private String url;

    @OneToOne
    @JoinColumn(unique = true)
    private DynamicDataEnvelope dynamicDataEnvelope;

    @OneToOne
    @JoinColumn(unique = true)
    private AuditInfo auditInfo;

    @ManyToOne
    @JsonIgnoreProperties(value = "fileSystems", allowSetters = true)
    private Discrim discrim;

    @ManyToOne
    @JsonIgnoreProperties(value = "fileSystems", allowSetters = true)
    private Envelope envelope;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FileSystem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public FileSystem data(byte[] data) {
        this.data = data;
        return this;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public FileSystem dataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
        return this;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public Boolean isExt() {
        return ext;
    }

    public FileSystem ext(Boolean ext) {
        this.ext = ext;
        return this;
    }

    public void setExt(Boolean ext) {
        this.ext = ext;
    }

    public String getUrl() {
        return url;
    }

    public FileSystem url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DynamicDataEnvelope getDynamicDataEnvelope() {
        return dynamicDataEnvelope;
    }

    public FileSystem dynamicDataEnvelope(DynamicDataEnvelope dynamicDataEnvelope) {
        this.dynamicDataEnvelope = dynamicDataEnvelope;
        return this;
    }

    public void setDynamicDataEnvelope(DynamicDataEnvelope dynamicDataEnvelope) {
        this.dynamicDataEnvelope = dynamicDataEnvelope;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public FileSystem auditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
        return this;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    public Discrim getDiscrim() {
        return discrim;
    }

    public FileSystem discrim(Discrim discrim) {
        this.discrim = discrim;
        return this;
    }

    public void setDiscrim(Discrim discrim) {
        this.discrim = discrim;
    }

    public Envelope getEnvelope() {
        return envelope;
    }

    public FileSystem envelope(Envelope envelope) {
        this.envelope = envelope;
        return this;
    }

    public void setEnvelope(Envelope envelope) {
        this.envelope = envelope;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileSystem)) {
            return false;
        }
        return id != null && id.equals(((FileSystem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileSystem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", data='" + getData() + "'" +
            ", dataContentType='" + getDataContentType() + "'" +
            ", ext='" + isExt() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}

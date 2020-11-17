package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Envelope.
 */
@Entity
@Table(name = "envelope")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Envelope implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_desc")
    private String desc;

    @OneToOne
    @JoinColumn(unique = true)
    private DynamicDataEnvelope dynamicDataEnvelope;

    @OneToOne
    @JoinColumn(unique = true)
    private AuditInfo auditInfo;

    @OneToMany(mappedBy = "envelope")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<FileSystem> fileSystems = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Envelope> envelopes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "envelopes", allowSetters = true)
    private Discrim discrim;

    @ManyToOne
    @JsonIgnoreProperties(value = "envelopes", allowSetters = true)
    private Envelope owner;

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

    public Envelope name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public Envelope desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DynamicDataEnvelope getDynamicDataEnvelope() {
        return dynamicDataEnvelope;
    }

    public Envelope dynamicDataEnvelope(DynamicDataEnvelope dynamicDataEnvelope) {
        this.dynamicDataEnvelope = dynamicDataEnvelope;
        return this;
    }

    public void setDynamicDataEnvelope(DynamicDataEnvelope dynamicDataEnvelope) {
        this.dynamicDataEnvelope = dynamicDataEnvelope;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public Envelope auditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
        return this;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    public Set<FileSystem> getFileSystems() {
        return fileSystems;
    }

    public Envelope fileSystems(Set<FileSystem> fileSystems) {
        this.fileSystems = fileSystems;
        return this;
    }

    public Envelope addFileSystem(FileSystem fileSystem) {
        this.fileSystems.add(fileSystem);
        fileSystem.setEnvelope(this);
        return this;
    }

    public Envelope removeFileSystem(FileSystem fileSystem) {
        this.fileSystems.remove(fileSystem);
        fileSystem.setEnvelope(null);
        return this;
    }

    public void setFileSystems(Set<FileSystem> fileSystems) {
        this.fileSystems = fileSystems;
    }

    public Set<Envelope> getEnvelopes() {
        return envelopes;
    }

    public Envelope envelopes(Set<Envelope> envelopes) {
        this.envelopes = envelopes;
        return this;
    }

    public Envelope addEnvelope(Envelope envelope) {
        this.envelopes.add(envelope);
        envelope.setOwner(this);
        return this;
    }

    public Envelope removeEnvelope(Envelope envelope) {
        this.envelopes.remove(envelope);
        envelope.setOwner(null);
        return this;
    }

    public void setEnvelopes(Set<Envelope> envelopes) {
        this.envelopes = envelopes;
    }

    public Discrim getDiscrim() {
        return discrim;
    }

    public Envelope discrim(Discrim discrim) {
        this.discrim = discrim;
        return this;
    }

    public void setDiscrim(Discrim discrim) {
        this.discrim = discrim;
    }

    public Envelope getOwner() {
        return owner;
    }

    public Envelope owner(Envelope envelope) {
        this.owner = envelope;
        return this;
    }

    public void setOwner(Envelope envelope) {
        this.owner = envelope;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Envelope)) {
            return false;
        }
        return id != null && id.equals(((Envelope) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Envelope{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}

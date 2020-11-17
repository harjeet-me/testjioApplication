package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DynamicDataEnvelope.
 */
@Entity
@Table(name = "dynamic_data_envelope")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DynamicDataEnvelope implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_desc")
    private String desc;

    @OneToOne
    @JoinColumn(unique = true)
    private AuditInfo auditInfo;

    @OneToMany(mappedBy = "dynamicDataEnvelope")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<DynamicData> dynamicData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public DynamicDataEnvelope desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public DynamicDataEnvelope auditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
        return this;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    public Set<DynamicData> getDynamicData() {
        return dynamicData;
    }

    public DynamicDataEnvelope dynamicData(Set<DynamicData> dynamicData) {
        this.dynamicData = dynamicData;
        return this;
    }

    public DynamicDataEnvelope addDynamicData(DynamicData dynamicData) {
        this.dynamicData.add(dynamicData);
        dynamicData.setDynamicDataEnvelope(this);
        return this;
    }

    public DynamicDataEnvelope removeDynamicData(DynamicData dynamicData) {
        this.dynamicData.remove(dynamicData);
        dynamicData.setDynamicDataEnvelope(null);
        return this;
    }

    public void setDynamicData(Set<DynamicData> dynamicData) {
        this.dynamicData = dynamicData;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DynamicDataEnvelope)) {
            return false;
        }
        return id != null && id.equals(((DynamicDataEnvelope) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DynamicDataEnvelope{" +
            "id=" + getId() +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}

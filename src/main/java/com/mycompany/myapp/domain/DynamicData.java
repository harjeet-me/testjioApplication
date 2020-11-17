package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.mycompany.myapp.domain.enumeration.DataType;

/**
 * A DynamicData.
 */
@Entity
@Table(name = "dynamic_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DynamicData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_key")
    private String dataKey;

    @Column(name = "data_value")
    private String dataValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_data_type")
    private DataType valueDataType;

    @OneToOne
    @JoinColumn(unique = true)
    private AuditInfo auditInfo;

    @ManyToOne
    @JsonIgnoreProperties(value = "dynamicData", allowSetters = true)
    private DynamicDataEnvelope dynamicDataEnvelope;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataKey() {
        return dataKey;
    }

    public DynamicData dataKey(String dataKey) {
        this.dataKey = dataKey;
        return this;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getDataValue() {
        return dataValue;
    }

    public DynamicData dataValue(String dataValue) {
        this.dataValue = dataValue;
        return this;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public DataType getValueDataType() {
        return valueDataType;
    }

    public DynamicData valueDataType(DataType valueDataType) {
        this.valueDataType = valueDataType;
        return this;
    }

    public void setValueDataType(DataType valueDataType) {
        this.valueDataType = valueDataType;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public DynamicData auditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
        return this;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    public DynamicDataEnvelope getDynamicDataEnvelope() {
        return dynamicDataEnvelope;
    }

    public DynamicData dynamicDataEnvelope(DynamicDataEnvelope dynamicDataEnvelope) {
        this.dynamicDataEnvelope = dynamicDataEnvelope;
        return this;
    }

    public void setDynamicDataEnvelope(DynamicDataEnvelope dynamicDataEnvelope) {
        this.dynamicDataEnvelope = dynamicDataEnvelope;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DynamicData)) {
            return false;
        }
        return id != null && id.equals(((DynamicData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DynamicData{" +
            "id=" + getId() +
            ", dataKey='" + getDataKey() + "'" +
            ", dataValue='" + getDataValue() + "'" +
            ", valueDataType='" + getValueDataType() + "'" +
            "}";
    }
}

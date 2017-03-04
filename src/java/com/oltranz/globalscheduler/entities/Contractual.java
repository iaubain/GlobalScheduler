/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Hp
 */
@Entity
@Table(name = "Contractual",uniqueConstraints = {@UniqueConstraint(columnNames = {"contractId"})})
public class Contractual implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    //my entity properties
    @Column(name="contractId",nullable=false, length = 600)
    private String contractId;
    
    @Column(name="appName",nullable=false)
    private String appName;
    
    @Column(name="location",nullable=false)
    private String location;
    
    @Column(name="status",nullable=false)
    private int status;
    
    @Column(name="description",nullable=false)
    private String description;
    
    @Column(name="created",nullable=true)    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;
    
    @Column(name="ended",nullable=true)    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date ended;

    public Contractual() {
    }

    public Contractual(String contractId, String appName, String location, int status, String description, Date created, Date ended) {
        this.contractId = contractId;
        this.appName = appName;
        this.location = location;
        this.status = status;
        this.description = description;
        this.created = created;
        this.ended = ended;
    }
    
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contractual)) {
            return false;
        }
        Contractual other = (Contractual) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public java.lang.String toString() {
        return "com.oltranz.globalscheduler.entities.Contractual[ id=" + id + " ]";
    }

    /**
     * @return the contractId
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * @param contractId the contractId to set
     */
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    /**
     * @return the appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName the appName to set
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the ended
     */
    public Date getEnded() {
        return ended;
    }

    /**
     * @param ended the ended to set
     */
    public void setEnded(Date ended) {
        this.ended = ended;
    }
    
}

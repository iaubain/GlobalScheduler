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
@Table(name = "Jobs",uniqueConstraints = {@UniqueConstraint(columnNames = {"jobId"})})
public class Jobs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    //my entity properties
    @Column(name="jobId",nullable=false)
    private String jobId;
    
    @Column(name="timeFrame",nullable=false)
    private String timeFrame;
    
    @Column(name="objectDomain",nullable=false)
    private String objectDomain;
    
    @Column(name="reportChanel",nullable=false, length = 250)
    private String reportChanel;
    
    @Column(name="creation",nullable=false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creation;
    
    @Column(name="expiration",nullable=false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date expiration;
    
    @Column(name="status",nullable=false)
    private int status;
    
    @Column(name="description",nullable=false)
    private String description;
    
    @Column(name="freq",nullable=true, length = 1024)
    private String freq;
    
    @Column(name="isChained",nullable=false)
    private boolean isChained;
    
    @Column(name="contarctId",nullable=false)
    private String conractId;

    public Jobs() {
    }

    public Jobs(String jobId, String timeFrame, String objectDomain, String reportChanel, Date creation, Date expiration, int status, String description, String freq, boolean isChained, String contractId) {
        this.jobId = jobId;
        this.timeFrame = timeFrame;
        this.objectDomain = objectDomain;
        this.reportChanel = reportChanel;
        this.creation = creation;
        this.expiration = expiration;
        this.status = status;
        this.description = description;
        this.freq = freq;
        this.isChained = isChained;
        this.conractId = contractId;
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
        if (!(object instanceof Jobs)) {
            return false;
        }
        Jobs other = (Jobs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oltranz.globalscheduler.entities.Jobs[ id=" + id + " ]";
    }

    /**
     * @return the jobId
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * @param jobId the jobId to set
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * @return the timeFrame
     */
    public String getTimeFrame() {
        return timeFrame;
    }

    /**
     * @param timeFrame the timeFrame to set
     */
    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    /**
     * @return the objectDomain
     */
    public String getObjectDomain() {
        return objectDomain;
    }

    /**
     * @param objectDomain the objectDomain to set
     */
    public void setObjectDomain(String objectDomain) {
        this.objectDomain = objectDomain;
    }

    /**
     * @return the reportChanel
     */
    public String getReportChanel() {
        return reportChanel;
    }

    /**
     * @param reportChanel the reportChanel to set
     */
    public void setReportChanel(String reportChanel) {
        this.reportChanel = reportChanel;
    }

    /**
     * @return the creation
     */
    public Date getCreation() {
        return creation;
    }

    /**
     * @param creation the creation to set
     */
    public void setCreation(Date creation) {
        this.creation = creation;
    }

    /**
     * @return the expiration
     */
    public Date getExpiration() {
        return expiration;
    }

    /**
     * @param expiration the expiration to set
     */
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
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
     * @return the isChained
     */
    public boolean isIsChained() {
        return isChained;
    }

    /**
     * @param isChained the isChained to set
     */
    public void setIsChained(boolean isChained) {
        this.isChained = isChained;
    }

    /**
     * @return the freq
     */
    public String getFreq() {
        return freq;
    }

    /**
     * @param freq the freq to set
     */
    public void setFreq(String freq) {
        this.freq = freq;
    }

    /**
     * @return the conractId
     */
    public String getConractId() {
        return conractId;
    }

    /**
     * @param conractId the conractId to set
     */
    public void setConractId(String conractId) {
        this.conractId = conractId;
    }
    
}

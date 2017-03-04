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
import javax.persistence.Temporal;

/**
 *
 * @author Hp
 */
@Entity
public class Tasks implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String taskId;
    
    //my entity property
    //taskId, jobId, objectDomain, creation, ended, status, desc, frequency
    @Column(name="jobId",nullable=false)
    private String jobId;
    
    @Column(name="objectDomain",nullable=false)
    private String objectDomain;
    
    @Column(name="freq",nullable=true, length = 1024)
    private String freq;
    
    @Column(name="creation",nullable=false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)            
    private Date creation;
    
    @Column(name="ended",nullable=true)    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date ended;
    
    @Column(name="status",nullable=false)    
    private int status;
    
    @Column(name="description",nullable=false)    
    private String description;
    
    @Column(name="reportChanel",nullable=false, length = 250)
    private String reportChanel;
    
    @Column(name="position",nullable=false, length = 250)
    private int position;

    public Tasks() {
    }

    public Tasks(String jobId, String objectDomain, String freq, Date creation, Date ended, int status, String description, String reportChanel, int position) {
        this.jobId = jobId;
        this.objectDomain = objectDomain;
        this.freq = freq;
        this.creation = creation;
        this.ended = ended;
        this.status = status;
        this.description = description;
        this.reportChanel = reportChanel;
        this.position = position;
    }
    
    

    public String getId() {
        return taskId;
    }

    public void setId(String id) {
        this.taskId = id;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskId != null ? taskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the taskId fields are not set
        if (!(object instanceof Tasks)) {
            return false;
        }
        Tasks other = (Tasks) object;
        if ((this.taskId == null && other.taskId != null) || (this.taskId != null && !this.taskId.equals(other.taskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oltranz.globalscheduler.entities.Tasks[ id=" + taskId + " ]";
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
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int compareTo(Object tasks) {
        int comparePosition=((Tasks)tasks).getPosition();
        /* For Ascending order*/
        return this.position-comparePosition;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.config;

/**
 *
 * @author Hp
 */
public class StatusConfig {
    public static final String APP_DESC="SCHEDULER: ";
    
    public static final int WORKING_ST = 001;
    public static final String WORKING_ST_DESC="WORKING";
    
    public static final int PAUSED = 002;
    public static final String PAUSED_DESC="PAUSED";
    
    public static final int EXPIRED = 003;
    public static final String EXPIRED_DESC = "EXPIRED";
    
    public static final int ENDED = 004;
    public static final String ENDED_DESC = "ENDED";
    
    public static final int CANCELED = 005;
    public static final String CANCELED_DESC = "CANCELED";
    
    public static final int CREATED = 006;
    public static final String CREATED_DESC = "CREATED";
    
    public static final int PENDING = 007;
    public static final String PENDING_DESC = "PENDING";
}

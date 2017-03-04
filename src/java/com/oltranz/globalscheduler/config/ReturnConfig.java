/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.config;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Hp
 */
public class ReturnConfig {
    
    public static final Response isSuccess( String outPut){
        return Response.ok(outPut, MediaType.WILDCARD_TYPE).build();
    }
    
    public static final Response isFailed(String cause){
        return Response.status(Status.NOT_ACCEPTABLE).type(MediaType.WILDCARD_TYPE).entity("Error: "+cause).build();
    }
}

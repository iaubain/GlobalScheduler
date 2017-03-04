/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.utilities.reporters;

import com.oltranz.globalscheduler.config.HeaderConfig;
import com.oltranz.globalscheduler.config.StatusConfig;
import static java.lang.System.out;
import java.util.HashMap;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author Hp
 */
public class ReportManager {
    String taskId;
    String jobId;
    HashMap<String, String> headerList;
    String url;

    public ReportManager(String taskId, String jobId, HashMap<String, String> headerList, String url) {
        this.taskId = taskId;
        this.jobId = jobId;
        this.headerList = headerList;
        this.url = url;
    }
    
    
    public void makeReport(){
        try{
            Client client = ClientBuilder.newClient();
        WebTarget target =client.target(url);
        Response response = target.request()        
                .header(HeaderConfig.JOB_ID, headerList.get(HeaderConfig.JOB_ID))
                .header(HeaderConfig.JOB_DOMAIN, headerList.get(HeaderConfig.JOB_DOMAIN))
                .header(HeaderConfig.TASK_ID, headerList.get(HeaderConfig.TASK_ID))
                .header(HeaderConfig.TASK_DOMAIN, headerList.get(HeaderConfig.TASK_DOMAIN))
                .header(HeaderConfig.JOB_STATUS, headerList.get(StatusConfig.WORKING_ST_DESC))
                .get();
        
        out.print(StatusConfig.APP_DESC+" Reporting on Url "+url+" Job: "+jobId+" Task: "+taskId);
        
        String resp=response.readEntity(String.class);
        out.print(StatusConfig.APP_DESC+" Reporting result on Url "+url+" Job: "+jobId+" Task: "+taskId+" "+resp);
        }catch(Exception e){
            out.print(StatusConfig.APP_DESC+" Reporting Failed on Url "+url+" Job: "+jobId+" Task: "+taskId+" "+e.getLocalizedMessage());
        }
    }
}

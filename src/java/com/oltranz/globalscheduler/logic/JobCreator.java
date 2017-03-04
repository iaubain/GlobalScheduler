/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.logic;

import com.oltranz.globalscheduler.config.StatusConfig;
import com.oltranz.globalscheduler.entities.Jobs;
import com.oltranz.globalscheduler.entities.Tasks;
import com.oltranz.globalscheduler.fascades.JobsFacade;
import com.oltranz.globalscheduler.fascades.TasksFacade;
import com.oltranz.globalscheduler.simplebeans.frequencybean.MyFrequency;
import com.oltranz.globalscheduler.simplebeans.myjobs.JobTasks;
import com.oltranz.globalscheduler.simplebeans.myjobs.MyJob;
import com.oltranz.globalscheduler.utilities.DataFactory;
import static java.lang.System.out;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Hp
 */
@Stateless
public class JobCreator {
    
    @EJB
    JobsFacade mJob;
    
    @EJB
    TasksFacade mTask;

    public boolean createJob(MyJob myJob, String contractId) {         
        if(myJob == null)
            return false;
        
        if(contractId == null)
            return false;
        
        List<JobTasks> mJobTasks = myJob.getmTasks();
        if(mJobTasks.isEmpty())
            return false;
        
        if(! areTaskBelongToJob(myJob, mJobTasks))
            return false;
        
        try{
            String freqValue;
                    MyFrequency mFrequency = myJob.getFrequency();
                    if(mFrequency == null)
                        freqValue="none";
                    else
                        freqValue = DataFactory.objectToString(myJob.getFrequency());
                    
            out.print(StatusConfig.APP_DESC+"Creating Job: "+myJob.getJobId());
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date expiration = formatter.parse(myJob.getExpiration().replaceAll("Z$", "+0000"));
            Jobs jobs = new Jobs(myJob.getJobId(), 
                    myJob.getTimeFrame(),
                    myJob.getObjectDomain(),
                    myJob.getReportChanel(), 
                    new Date(), 
                    expiration, 
                    StatusConfig.CREATED, 
                    StatusConfig.CREATED_DESC, 
                    freqValue,
                    myJob.isIsChained(),
                    contractId);
            
            mJob.create(jobs);
            
            mJobTasks.stream().forEach((jobTask) -> {
                try{
                    String taskFreqValue;
                    MyFrequency taskFrequency = jobTask.getmFrequency();
                    if(taskFrequency == null)
                        taskFreqValue="none";
                    else
                        taskFreqValue = DataFactory.objectToString(jobTask.getmFrequency());                    
                   
                    if(myJob.getJobId().equals(jobTask.getJobId())){
                    Tasks task = new Tasks(myJob.getJobId(), 
                            jobTask.getObjectDomain(),
                            taskFreqValue,
                            new Date(),
                            new Date(),
                            StatusConfig.CREATED,
                            StatusConfig.CREATED_DESC,
                            jobTask.getReportChanel(), 
                            jobTask.getPosition());
                    
                    mTask.create(task);
                  }else{
                        out.print(StatusConfig.APP_DESC+"Failed to create a Task of Job "+jobTask.getJobId()+" from Job "+myJob.getJobId()+" due to JobId Mismatch");
                    }
                }catch(Exception e){
                    out.print(StatusConfig.APP_DESC+"Failed to create a Task due to Error  "+e.getCause().getLocalizedMessage());
                }
            });
        }catch(ParseException e){
            out.print(StatusConfig.APP_DESC+"Failed to create a Job due to Error  "+e.getCause().getLocalizedMessage());
            return false;
        }
        
        return true;
    }
    
    private boolean areTaskBelongToJob(MyJob myJob, List<JobTasks> mJobTasks){
        return mJobTasks.stream().anyMatch((jobTasks) -> (myJob.getJobId().equals(jobTasks.getJobId())));
    }
}

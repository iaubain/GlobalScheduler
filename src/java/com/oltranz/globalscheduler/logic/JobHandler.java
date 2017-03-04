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
import com.oltranz.globalscheduler.logic.timers.JobScheduler;
import com.oltranz.globalscheduler.simplebeans.frequencybean.MyFrequency;
import com.oltranz.globalscheduler.simplebeans.myjobs.CancelSceduledJob;
import com.oltranz.globalscheduler.simplebeans.myjobs.JobTasks;
import com.oltranz.globalscheduler.simplebeans.myjobs.MyJob;
import com.oltranz.globalscheduler.utilities.DataFactory;
import com.oltranz.globalscheduler.utilities.TimerValidator;
import static java.lang.System.out;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Hp
 */
@Stateless
public class JobHandler {
    
    @EJB
    JobScheduler jobScheduler;
    
    @EJB
    JobsFacade jobsFacade;
    
    @EJB
    TasksFacade tasksFacade;
    
    @EJB
    JobCreator jobCreator;

   public boolean createJob(String body, String contractId){
       out.print(StatusConfig.APP_DESC+" CommandExec JobHandler is handling a job :\n"+body);
        try{
            MyJob myJob = (MyJob)DataFactory.stringToObject(new MyJob(), body);
            
            //validate schedule valiables
           if(myJob.getmTasks().size() <= 0)
               return false;
           
           boolean isJobSaved = jobCreator.createJob(myJob, contractId);
           
           if(isJobSaved){
               //proceed with getting the saved entry
               if(myJob.isIsChained()){
                   long delayExpression = chainedJob(myJob);
               if(delayExpression == 0){
                   //update this job as cancelled
                   Jobs jobs = jobsFacade.getJobById(myJob.getJobId());
                   if(jobs == null)
                       return false;
                   jobs.setStatus(StatusConfig.CANCELED);
                   jobs.setDescription(StatusConfig.CANCELED_DESC);
                   jobsFacade.edit(jobs);
                   return false;
               }else{
                   //put this job on background thread
                   Jobs jobs = jobsFacade.getJobById(myJob.getJobId());
                   if(jobs == null)
                       return false;
                   try{                   
                       jobScheduler.createChainedJob(delayExpression, jobs);
                   }catch(Exception e){
                       out.print(StatusConfig.APP_DESC+" Error Creating background thread");
                       return false;
                   }
                   return true;
               }
               }else{
                   //handle unchained job
                   List<Long> expressionList = unChainedJobTimerValidation(myJob);
                   if(expressionList.isEmpty())
                        return false;
                  List<Tasks> taskList = tasksFacade.getTasksByJob(myJob.getJobId());
                  taskList.forEach((task) -> {
                      long delayExpression = unChainedJob(myJob, task);
                      try{
                          jobScheduler.createUnchainedJob(delayExpression, task);
                      }catch(Exception e){
                          out.print(StatusConfig.APP_DESC+" Error Creating background thread");
                      }
                   });
               }
           }else{
               return false;
           }
           
           return true;
        }catch(Exception e){
            out.print(StatusConfig.APP_DESC+" CommandExec jobHandler failed to mapping : "+body+" due to Error: "+e.getLocalizedMessage());
            return false;
        }
   }
   
   public boolean cancelJob(String body, String contractId){
       out.print(StatusConfig.APP_DESC+" CommandExec jobHandler is canceling : "+body);
       try{
           CancelSceduledJob cancelSceduledJob = (CancelSceduledJob) DataFactory.stringToObject(new CancelSceduledJob(), body);
           if(cancelSceduledJob == null){
               return false;
           }
           try{
               Jobs jobs = jobsFacade.getJobById(cancelSceduledJob.getJobId());
               if(jobs == null){
                   out.print(StatusConfig.APP_DESC+" CommandExec jobHandler No job found to be cancelled for: "+body);
                   return false;                  
               }
               if(!jobs.getConractId().equals(contractId)){
                   out.print(StatusConfig.APP_DESC+" CommandExec jobHandler try to cancel the job initiated with diffrent contract "+body);
                   return false;
               }
               jobs.setStatus(StatusConfig.CANCELED);
               jobs.setDescription(StatusConfig.CANCELED_DESC);
               jobsFacade.edit(jobs);
               return true;
           }catch(Exception e){
               out.print(StatusConfig.APP_DESC+" CommandExec jobHandler failed to cancel job : "+body+" due to Error: "+e.getLocalizedMessage());
                return false;
           }
       }catch(Exception e){
           out.print(StatusConfig.APP_DESC+" CommandExec jobHandler failed to mapping : "+body+" due to Error: "+e.getLocalizedMessage());
            return false;
       }
   }
   
    private Long chainedJob(MyJob myJob){
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date expiration = formatter.parse(myJob.getExpiration().replaceAll("Z$", "+0000"));
            return new TimerValidator(myJob.getFrequency(), expiration).isValidTimer();
        } catch (ParseException ex) {
            Logger.getLogger(CommandExec.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private List<Long> unChainedJobTimerValidation(MyJob myJob){
        int taskSize, validityCounter;
        List<Long> delayExpression = new ArrayList<>();
        List<JobTasks> taskList = myJob.getmTasks();
        if(taskList.isEmpty())
            return delayExpression;
        taskSize = taskList.size();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expiration;
        try {
            expiration = formatter.parse(myJob.getExpiration().replaceAll("Z$", "+0000"));
        } catch (ParseException ex) {
            Logger.getLogger(CommandExec.class.getName()).log(Level.SEVERE, null, ex);
            return delayExpression;
        }
        taskList.stream().map((task) -> new TimerValidator(task.getmFrequency(), expiration).isValidTimer()).filter((scheduleExpression) -> (scheduleExpression != null && scheduleExpression != 0)).forEach((scheduleExpression) -> {
            delayExpression.add(scheduleExpression);
        });
        return delayExpression;
    }
    
    private long unChainedJob(MyJob myJob, Tasks task){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expiration;
        try {
            expiration = formatter.parse(myJob.getExpiration().replaceAll("Z$", "+0000"));
        } catch (ParseException ex) {
            Logger.getLogger(CommandExec.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return new TimerValidator((MyFrequency) DataFactory.stringToObject(new MyFrequency(), task.getFreq()), expiration).isValidTimer();
    }
}

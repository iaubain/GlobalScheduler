/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.logic.timers;

import com.oltranz.globalscheduler.config.HeaderConfig;
import com.oltranz.globalscheduler.config.StatusConfig;
import com.oltranz.globalscheduler.entities.Jobs;
import com.oltranz.globalscheduler.entities.Tasks;
import com.oltranz.globalscheduler.fascades.JobsFacade;
import com.oltranz.globalscheduler.fascades.TasksFacade;
import com.oltranz.globalscheduler.utilities.reporters.ReportManager;
import static java.lang.System.out;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.ejb.EJB;

/**
 *
 * @author Hp
 */
public class ScheduledJob extends TimerTask{
    
    @EJB
    JobsFacade jobsFacade;  
    
    @EJB
    TasksFacade tasksFacade;
    
    Tasks jobTasks;
    
    Jobs myJob;
    
    boolean isChained;
    
    Timer timer;

    public ScheduledJob(Timer timer, JobsFacade jobsFacade, TasksFacade tasksFacade, Tasks jobTasks, Jobs myJob, boolean isChained) {
        this.jobsFacade = jobsFacade;
        this.tasksFacade = tasksFacade;
        this.jobTasks = jobTasks;
        this.myJob = myJob;
        this.isChained = isChained;
        this.timer = timer;
    }
    
    

    @Override
    public void run() {
        validateSchedule();
    }
    
    private void validateSchedule(){
        if(isChained){
            runChainedJob();
        }else{
            runUnChainedJob();
        }
    }
    
    private void runChainedJob(){
        try{
            out.print(StatusConfig.APP_DESC+" running chained job "+ myJob.getJobId());
            
            Jobs job = jobsFacade.getJobById(myJob.getJobId());
            if(job.getStatus() == StatusConfig.CANCELED){
                out.print(StatusConfig.APP_DESC+" "+job.getJobId()+" Job has been cancelled.");
                List<Tasks> jobTaskList = tasksFacade.getTasksByJob(myJob.getJobId());
                cancelledJob(jobTaskList, new Date());
                timer.cancel();
                return;
            }
            
            
            
        List<Tasks> jobTaskList = tasksFacade.getTasksByJob(myJob.getJobId());
            if(jobTaskList.size() > 0){
                Collections.sort(jobTaskList);
                
                for(Tasks tasks : jobTaskList){
                    Jobs jobs = jobsFacade.getJobById(tasks.getJobId());
                    Date date = new Date();
                    out.print(StatusConfig.APP_DESC+" "+jobs.getJobId()+" Job Expitration is in: "+ jobs.getExpiration().getTime()+" Current time is: "+date.getTime());
                    if(jobs.getExpiration().getTime()< date.getTime()){
                        out.print(StatusConfig.APP_DESC+" Job timedout "+ jobs.getJobId());
                        jobs.setStatus(StatusConfig.EXPIRED);
                        jobs.setDescription(StatusConfig.EXPIRED_DESC);
                        jobsFacade.edit(jobs);
                        
                        tasks.setEnded(date);
                        tasksFacade.edit(tasks);
                        
                        //triger last report
                        HashMap<String, String> headerList = new HashMap<>();
                        headerList.put(HeaderConfig.JOB_ID, jobs.getJobId());
                        headerList.put(HeaderConfig.JOB_DOMAIN, jobs.getObjectDomain());
                        headerList.put(HeaderConfig.TASK_ID, tasks.getId());
                        headerList.put(HeaderConfig.TASK_DOMAIN, tasks.getObjectDomain());
                        headerList.put(HeaderConfig.JOB_STATUS, StatusConfig.WORKING_ST_DESC);
                        
                        String reportUrl;
                        if(jobs.getReportChanel().toLowerCase().isEmpty() || jobs.getReportChanel().toLowerCase().equals("none"))
                            reportUrl = tasks.getReportChanel();
                        else
                            reportUrl = jobs.getReportChanel();
                        requestReport(headerList, reportUrl, jobs.getJobId(), tasks.getId());
                        timer.cancel();
                    }else{
                       out.print(StatusConfig.APP_DESC+" Job is alive "+ jobs.getJobId());
                       //triger report
                        HashMap<String, String> headerList = new HashMap<>();
                        headerList.put(HeaderConfig.JOB_ID, jobs.getJobId());
                        headerList.put(HeaderConfig.JOB_DOMAIN, jobs.getObjectDomain());
                        headerList.put(HeaderConfig.TASK_ID, tasks.getId());
                        headerList.put(HeaderConfig.TASK_DOMAIN, tasks.getObjectDomain());
                        headerList.put(HeaderConfig.JOB_STATUS, StatusConfig.WORKING_ST_DESC);
                        
//                        MultivaluedMap<String, Object> headerList = new MultivaluedHashMap<>();
//                        headerList.add(HeaderConfig.JOB_ID, jobs.getJobId());
//                        headerList.add(HeaderConfig.JOB_DOMAIN, jobs.getObjectDomain());
//                        headerList.add(HeaderConfig.TASK_ID, tasks.getId());
//                        headerList.add(HeaderConfig.TASK_DOMAIN, tasks.getObjectDomain());
//                        headerList.add(HeaderConfig.JOB_STATUS, StatusConfig.WORKING_ST_DESC);
                        
                        String reportUrl = "";
                        if(jobs.getReportChanel().toLowerCase().isEmpty() || jobs.getReportChanel().toLowerCase().equals("none"))
                            reportUrl = tasks.getReportChanel();
                        else
                            reportUrl = jobs.getReportChanel();
                        requestReport(headerList, reportUrl, jobs.getJobId(), tasks.getId());
                    }
                }
            }else{
                //invalid job that needs to be cleaned
                myJob.setStatus(StatusConfig.CANCELED);
                myJob.setDescription(StatusConfig.CANCELED_DESC);
                jobsFacade.edit(myJob);
                timer.cancel();
            }
            
            }catch(Exception e){
            out.print(StatusConfig.APP_DESC+" Failed to execute chained job");
            timer.cancel();
        }
    }
    
    private void runUnChainedJob(){
        try{
            out.print(StatusConfig.APP_DESC+" running unchained job "+ jobTasks.getJobId());
            
            Jobs job = jobsFacade.getJobById(jobTasks.getJobId());
            if(job == null){
                out.print(StatusConfig.APP_DESC+" couldn't find job "+ jobTasks.getJobId());
                return;               
            }
            
            if(job.getStatus() == StatusConfig.CANCELED){
                out.print(StatusConfig.APP_DESC+" "+job.getJobId()+" Job has been cancelled.");
                List<Tasks> jobTaskList = tasksFacade.getTasksByJob(jobTasks.getJobId());
                cancelledJob(jobTaskList, new Date());
                timer.cancel();
                return;
            }
            
            List<Tasks> jobTaskList = tasksFacade.getTasksByJob(jobTasks.getJobId());
            if(jobTaskList.size() > 0){                
                for(Tasks tasks : jobTaskList){
                    Jobs jobs = jobsFacade.getJobById(tasks.getJobId());
                    Date date = new Date();
                    out.print(StatusConfig.APP_DESC+" "+jobs.getJobId()+" Job Expitration is in: "+ jobs.getExpiration().getTime()+" Current time is: "+date.getTime());
                    if(jobs.getExpiration().getTime()< date.getTime()){
                        out.print(StatusConfig.APP_DESC+" Job timedout "+ jobs.getJobId());
                        jobs.setStatus(StatusConfig.EXPIRED);
                        jobs.setDescription(StatusConfig.EXPIRED_DESC);
                        jobsFacade.edit(jobs);
                        
                        tasks.setEnded(date);
                        tasksFacade.edit(tasks);
                        
                        //triger last report
                        HashMap<String, String> headerList = new HashMap<>();
                        headerList.put(HeaderConfig.JOB_ID, jobs.getJobId());
                        headerList.put(HeaderConfig.JOB_DOMAIN, jobs.getObjectDomain());
                        headerList.put(HeaderConfig.TASK_ID, tasks.getId());
                        headerList.put(HeaderConfig.TASK_DOMAIN, tasks.getObjectDomain());
                        headerList.put(HeaderConfig.JOB_STATUS, StatusConfig.WORKING_ST_DESC);
                        
                        String reportUrl;
                        if(tasks.getReportChanel().toLowerCase().isEmpty() || tasks.getReportChanel().toLowerCase().equals("none"))
                            reportUrl = jobs.getReportChanel();
                        else
                            reportUrl = tasks.getReportChanel();
                        requestReport(headerList, reportUrl, jobs.getJobId(), tasks.getId());
                        timer.cancel();
                    }else{
                       out.print(StatusConfig.APP_DESC+" Job "+ jobs.getJobId()+" Task"+tasks.getId()+" is alive");
                       //triger report
                        HashMap<String, String> headerList = new HashMap<>();
                        headerList.put(HeaderConfig.JOB_ID, jobs.getJobId());
                        headerList.put(HeaderConfig.JOB_DOMAIN, jobs.getObjectDomain());
                        headerList.put(HeaderConfig.TASK_ID, tasks.getId());
                        headerList.put(HeaderConfig.TASK_DOMAIN, tasks.getObjectDomain());
                        headerList.put(HeaderConfig.JOB_STATUS, StatusConfig.WORKING_ST_DESC);
                     
                        
                        String reportUrl;
                        if(tasks.getReportChanel().toLowerCase().isEmpty() || tasks.getReportChanel().toLowerCase().equals("none"))
                            reportUrl = jobs.getReportChanel();
                        else
                            reportUrl = tasks.getReportChanel();
                        requestReport(headerList, reportUrl, jobs.getJobId(), tasks.getId());
                    }
                }
            }else{
                //invalid job that needs to be cleaned
                job.setStatus(StatusConfig.CANCELED);
                job.setDescription(StatusConfig.CANCELED_DESC);
                jobsFacade.edit(job);
                timer.cancel();
            }
        }catch(Exception e){
            out.print(StatusConfig.APP_DESC+" Failed to execute unChained job's Task due to "+ e.getLocalizedMessage());
            timer.cancel();
        }
    }
    
    private void cancelledJob(List<Tasks> jobTaskList, Date date){
        List<Tasks> tempList = jobTaskList;
        for(Tasks task : tempList){
            out.print(StatusConfig.APP_DESC+" Task "+task.getId()+" is being cancelled.");
            task.setStatus(StatusConfig.CANCELED);
            task.setEnded(date);
            tasksFacade.edit(task);
        }
    }
    private void requestReport(HashMap<String, String> headerList, String reportUrl, String jobId, String taskId){
        ReportManager reportManager = new ReportManager(taskId, jobId, headerList, reportUrl);
        reportManager.makeReport();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.logic.timers;

import com.oltranz.globalscheduler.entities.Jobs;
import com.oltranz.globalscheduler.entities.Tasks;
import com.oltranz.globalscheduler.fascades.JobsFacade;
import com.oltranz.globalscheduler.fascades.TasksFacade;
import com.oltranz.globalscheduler.simplebeans.myjobs.JobTasks;
import com.oltranz.globalscheduler.simplebeans.myjobs.MyJob;
import java.util.Timer;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

/**
 *
 * @author Hp
 */
@Stateless
public class JobScheduler{

   @Resource
   private SessionContext context;
   
   @EJB
    JobsFacade jobsFacade;  
    
    @EJB
    TasksFacade tasksFacade;
    
    JobTasks jobTasks;
    
    MyJob myJob;
    
    boolean isChained;
    

    public void createChainedJob(long scheduleExpression, Jobs myJob) {
        Timer timer = new Timer();
        ScheduledJob scheduledJob = new ScheduledJob(timer, jobsFacade, tasksFacade, null, myJob, true);
        timer.schedule(scheduledJob, 0, scheduleExpression);
    }

    public void createUnchainedJob(long scheduleExpression, Tasks task) {
        Timer timer = new Timer();
        ScheduledJob scheduledJob = new ScheduledJob(timer, jobsFacade, tasksFacade, task, null, false);
        timer.schedule(scheduledJob, 0, scheduleExpression);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.logic;

import com.oltranz.globalscheduler.config.CommandConfig;
import com.oltranz.globalscheduler.config.StatusConfig;
import static java.lang.System.out;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Hp
 */
@Stateless
public class CommandExec {    
    @EJB
    JobHandler jobHandler;
    
    @EJB
    MyContractual myContractual;
    
    
    public String exec(String cmd, String contractId, String body){
        if((!cmd.isEmpty()) && (!body.isEmpty())){
            out.print(StatusConfig.APP_DESC+" CommandExec receive and is executing cmd: "+cmd+" and Body "+body);
            String outPut = cmdHandler(cmd, contractId, body);
            if(outPut != null){
                out.print(StatusConfig.APP_DESC+" CommandExec Successfully executed cmd: "+cmd+" and Body "+body);
                return outPut;
            }else{
                out.print(StatusConfig.APP_DESC+" CommandExec failed to execute cmd: "+cmd+" and Body "+body);
                return null;
            }
        }
        
        out.print(StatusConfig.APP_DESC+" CommandExec failed to execute to cmd: "+cmd+" and Body "+body);
        return null;
    }
    
    private String cmdHandler(String cmd, String contractId, String body){
        switch(cmd){
            case CommandConfig.CREATE_JOB:
                if(jobHandler(body, contractId))
                    return "Success";
                return null;
            case CommandConfig.CANCEL_SCHEDULE:
                if(jobHandler.cancelJob(body, contractId))
                    return "Success";
                return null;
            case CommandConfig.CREATE_CONTRACT:
                return myContractual.createContract(body);
            case CommandConfig.CANCEL_CONTRACT:
                return myContractual.cancelContract(body);
            default:
                return null;
        }
    }
    
    private boolean jobHandler(String body, String contractId){
        return jobHandler.createJob(body, contractId);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.utilities;

import com.oltranz.globalscheduler.config.CommandConfig;
import com.oltranz.globalscheduler.config.StatusConfig;
import com.oltranz.globalscheduler.entities.Contractual;
import com.oltranz.globalscheduler.fascades.ContractualFacade;
import static java.lang.System.out;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Hp
 */
@Stateless
public class ValidateHeader {
    @EJB
    ContractualFacade contractualFacade;

    public boolean isValidContract(String contractId, String cmd) {
        try{
         out.print(StatusConfig.APP_DESC+"Validating contract: "+contractId);   
        if(cmd.equals(CommandConfig.CREATE_CONTRACT)){
            return true;
        }
            
        if(contractId == null)
            return false;
        if(contractId.equals("test0001")){
            out.print(StatusConfig.APP_DESC+"Test contract: "+contractId);
            return true;
        }else{
            Contractual contractual = contractualFacade.getContractById(contractId);
            if(contractual == null)
                return false;
            out.print(StatusConfig.APP_DESC+"Contract: "+contractual.getAppName());
            return !(contractual.getStatus() == StatusConfig.CANCELED || contractual.getStatus() == StatusConfig.PENDING);
        }   
        }catch(Exception e){
            out.print(StatusConfig.APP_DESC+" Failed to validate contract due to: Error "+e.getLocalizedMessage());
            return false;
        }
    }
    
    public boolean isValidCMD(String cmd){
        switch(cmd){
            case CommandConfig.CREATE_JOB:
                return true;
            case CommandConfig.CANCEL_SCHEDULE:
                return true;
            case CommandConfig.CREATE_CONTRACT:
                return true;
            case CommandConfig.CANCEL_CONTRACT:
                return true;
            default:
                return false;
        }
    }
    
}

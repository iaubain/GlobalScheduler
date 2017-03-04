/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.logic;

import com.oltranz.globalscheduler.config.StatusConfig;
import com.oltranz.globalscheduler.entities.Contractual;
import com.oltranz.globalscheduler.fascades.ContractualFacade;
import com.oltranz.globalscheduler.simplebeans.contractualbeans.CancelContract;
import com.oltranz.globalscheduler.simplebeans.contractualbeans.CreateContractRequest;
import com.oltranz.globalscheduler.simplebeans.contractualbeans.CreateContractResponse;
import com.oltranz.globalscheduler.utilities.DataFactory;
import static java.lang.System.out;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Hp
 */
@Stateless
public class MyContractual {

    @EJB
    ContractualFacade contractualFacade;
    
    public String createContract(String body){
        try{
            CreateContractRequest contractRequest = (CreateContractRequest) DataFactory.stringToObject(new CreateContractRequest(), body);
            if(contractRequest.getAppName() != null){
                //create contract
                String contractId = timeNow(new Date())+contractRequest.getAppName();
              
                Contractual contractual = new Contractual(contractId, 
                        contractRequest.getAppName(), 
                        contractRequest.getLocation(), 
                        StatusConfig.CREATED, 
                        contractRequest.getDescription(), 
                        new Date(),
                        null);               
                contractualFacade.create(contractual);
                return DataFactory.objectToString(new CreateContractResponse(contractId));
            }else{
                return null;
            }                
        }catch(Exception e){
            out.print(StatusConfig.APP_DESC+" Failed to map "+body+" due to "+ e.getLocalizedMessage());
            return null;
        }
    }
    
    public String cancelContract(String body){
        try{
            CancelContract cancelContract = (CancelContract) DataFactory.stringToObject(new CancelContract(), body);
            if(cancelContract != null){
                Contractual contractual = contractualFacade.getContractById(cancelContract.getContractId());
                contractual.setStatus(StatusConfig.CANCELED);
                contractual.setEnded(new Date());
                contractualFacade.edit(contractual);
                return "Cancelled";
            }
            return null;
        }catch(Exception e){
            out.print(StatusConfig.APP_DESC+" Cancelling Contract Failed "+ body+" due to "+ e.getLocalizedMessage());
            return null;
        }
    }
    
    private long timeNow(Date date){
        if(date == null)
            return new Date().getTime();
        return date.getTime();
    }
}

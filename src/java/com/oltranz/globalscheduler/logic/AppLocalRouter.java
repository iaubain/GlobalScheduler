/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.logic;

import com.oltranz.globalscheduler.config.HeaderConfig;
import com.oltranz.globalscheduler.config.ReturnConfig;
import com.oltranz.globalscheduler.config.StatusConfig;
import com.oltranz.globalscheduler.utilities.ValidateHeader;
import static java.lang.System.out;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author Hp
 */
@Stateless
public class AppLocalRouter {
    @EJB
    ValidateHeader validateHeader;
    
    @EJB
    CommandExec commandExec;
    
    public Response routing(HttpHeaders headers, String body) {
        if(headers == null){
            out.print(StatusConfig.APP_DESC+"Router receives Headers: isEmpty Body: "+body);
            return ReturnConfig.isFailed("Access denied");
        }
        
        out.print(StatusConfig.APP_DESC+"Router receives Headers:"+headers.toString()+"Body: "+body);
        
        
        if(headers.getHeaderString(HeaderConfig.CMD) != null){            
            if(validateHeader.isValidCMD(headers.getHeaderString(HeaderConfig.CMD))){
                
//                if(headers.getHeaderString(HeaderConfig.CONTRACT) != null){                   
                    if(validateHeader.isValidContract(headers.getHeaderString(HeaderConfig.CONTRACT), headers.getHeaderString(HeaderConfig.CMD))){
                        //redirect request to the proper handling class
                        String outPut = commandExec.exec(headers.getHeaderString(HeaderConfig.CMD),
                                headers.getHeaderString(HeaderConfig.CONTRACT),
                                body);
                        if(outPut != null){
                            out.print(StatusConfig.APP_DESC+"Router Report Header:"+
                                HeaderConfig.CMD+" : "+headers.getHeaderString(HeaderConfig.CMD)+"AndBody: "+body+" Are successful processed");
                            return ReturnConfig.isSuccess(outPut);
                        }else{
                            return ReturnConfig.isFailed("Internal error");
                        }                        
                    }else{
                        out.print(StatusConfig.APP_DESC+"Router Report Header:"+
                                HeaderConfig.CMD+" is: Invalid Command Body: "+body);
                        return ReturnConfig.isFailed("Invalid command");
                    }
//                }else{
//                    out.print(StatusConfig.APP_DESC+"Router Report Header:"+
//                                HeaderConfig.CMD+" is: An Empty Command Body: "+body);
//                    return ReturnConfig.isFailed("Access denied");
//                }
            }else{
                out.print(StatusConfig.APP_DESC+"Router Report Header:"+
                                HeaderConfig.CMD+" is: An Empty Command Body: "+body);
                return ReturnConfig.isFailed("Access denied");
            }        
        }else{
            return ReturnConfig.isFailed("Access denied");
        }        
    }
    
}

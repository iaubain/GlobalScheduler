/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.ws.service;

import com.oltranz.globalscheduler.config.StatusConfig;
import com.oltranz.globalscheduler.logic.AppLocalRouter;
import static java.lang.System.out;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Hp
 */
@Path("/declare")
@Stateless
public class WebServices {
    @EJB
    AppLocalRouter router;
    
    @Context
    private UriInfo context;
    
    @POST
    @Path("/action")
    @Consumes("application/json")
    public Response action(@Context HttpHeaders headers, String body,@QueryParam("user") String login,
		@QueryParam("password") String password){
        out.print(StatusConfig.APP_DESC+"Node Received Headers "+headers.toString()+" and Body  "+body);
        return router.routing(headers, body);
    }
}

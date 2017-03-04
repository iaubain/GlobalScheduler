/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.fascades;

import com.oltranz.globalscheduler.entities.Jobs;
import static java.lang.System.out;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Hp
 */
@Stateless
public class JobsFacade extends AbstractFacade<Jobs> {
    @PersistenceContext(unitName = "GlobalSchedulerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JobsFacade() {
        super(Jobs.class);
    }
    
    public Jobs getJobById(String jobId){
        try{
            if(jobId.isEmpty())
                return null;
            Query q= em.createQuery("Select J from Jobs J where J.jobId = :jobId");
            q.setParameter("jobId", jobId);
            List<Jobs> list = (List<Jobs>)q.getResultList();
            if(!list.isEmpty())
                return list.get(0);
            else
                return null;
        }catch(Exception ex){
            ex.printStackTrace(out);
            return null;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.fascades;

import com.oltranz.globalscheduler.entities.Jobs;
import com.oltranz.globalscheduler.entities.Tasks;
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
public class TasksFacade extends AbstractFacade<Tasks> {
    @PersistenceContext(unitName = "GlobalSchedulerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TasksFacade() {
        super(Tasks.class);
    }
    
    public List<Tasks> getTasksByJob(String jobId){
        try{
            if(jobId.isEmpty())
                return null;
            Query q= em.createQuery("Select T from Tasks T where T.jobId = :jobId");
            q.setParameter("jobId", jobId);
            List<Tasks> list = (List<Tasks>)q.getResultList();
            if(!list.isEmpty())
                return list;
            else
                return null;
        }catch(Exception ex){
            ex.printStackTrace(out);
            return null;
        }
    }
}

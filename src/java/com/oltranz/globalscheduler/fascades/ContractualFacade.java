/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.fascades;

import com.oltranz.globalscheduler.entities.Contractual;
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
public class ContractualFacade extends AbstractFacade<Contractual> {
    @PersistenceContext(unitName = "GlobalSchedulerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContractualFacade() {
        super(Contractual.class);
    }
    
    public Contractual getContractById(String contractualId){
        try{
            if(contractualId.isEmpty())
                return null;
            Query q= em.createQuery("Select C from Contractual C where C.contractId = :contractualId");
            q.setParameter("contractualId", contractualId);
            List<Contractual> list = (List<Contractual>)q.getResultList();
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

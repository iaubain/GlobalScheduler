/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.utilities;

import com.oltranz.globalscheduler.config.StatusConfig;
import com.oltranz.globalscheduler.config.TimerValueConfig;
import com.oltranz.globalscheduler.simplebeans.frequencybean.MyFrequency;
import static java.lang.System.out;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Hp
 */
public class TimerValidator {
    private final MyFrequency mFrequency;
    private final Date expires;

    public TimerValidator(MyFrequency mFrequency, Date expires) {
        this.mFrequency = mFrequency;
        this.expires = expires;
    }
    
    public long isValidTimer(){
        if(mFrequency == null)
            return 0;
        if(mFrequency.getKey() == null || mFrequency.getValue() == null)
            return 0;
        
        if(expires == null)
            return 0;
        
        if(expires.before(new Date()))
            return 0;
        
        switch(mFrequency.getKey()){
            case TimerValueConfig.SECOND:
                return secondExpression();
            case TimerValueConfig.MINUTE:
                return minuteExpression();
            case TimerValueConfig.HOUR:
                return hourExpression();
            case TimerValueConfig.DAY_OF_WEEK:
                return dayOfWeekExpression();
            case TimerValueConfig.DAY_OF_MONTH:
                return dayOfMonthExpression();
            case TimerValueConfig.MONTH:
                return monthExpression();
            case TimerValueConfig.YEAR:
                return yearExpression();
            default:
                return 0;
        }
    }
    
    private long secondExpression(){
        try{
            expressionLogger();
            long expValue = Long.valueOf(mFrequency.getValue());
            if(expValue > 1000){
                return expValue;
            }else{
                return 0;
            }
        }catch(NumberFormatException e){
            out.print(StatusConfig.APP_DESC+" TimerValidator failed due to "+ e.getLocalizedMessage());
            return 0;
        }
    }
    
    private long minuteExpression(){
        try{
            expressionLogger();
            long expValue = Long.valueOf(mFrequency.getValue());
            if(expValue > 59000){
                return expValue;
            }else{
                return 0;
            }
        }catch(NumberFormatException e){
            out.print(StatusConfig.APP_DESC+" TimerValidator failed due to "+ e.getLocalizedMessage());
            return 0;
        }
    }
    
    private long hourExpression(){
       try{
            expressionLogger();
            long expValue = Long.valueOf(mFrequency.getValue());
            if(expValue > 3599999){
                return expValue;
            }else{
                return 0;
            }
        }catch(NumberFormatException e){
            out.print(StatusConfig.APP_DESC+" TimerValidator failed due to "+ e.getLocalizedMessage());
            return 0;
        }
    }
    
    private long dayOfWeekExpression(){
        try{
            expressionLogger();
            long expValue = Long.valueOf(mFrequency.getValue());
            if(expValue > 1000){
                return expValue;
            }else{
                return 0;
            }
        }catch(NumberFormatException e){
            out.print(StatusConfig.APP_DESC+" TimerValidator failed due to "+ e.getLocalizedMessage());
            return 0;
        }
    }
    
    private long dayOfMonthExpression(){
        try{
            expressionLogger();
            long expValue = Long.valueOf(mFrequency.getValue());
            if(expValue > 1000){
                return expValue;
            }else{
                return 0;
            }
        }catch(NumberFormatException e){
            out.print(StatusConfig.APP_DESC+" TimerValidator failed due to "+ e.getLocalizedMessage());
            return 0;
        }
    }
    
    private long monthExpression(){
        try{
            expressionLogger();
            long expValue = Long.valueOf(mFrequency.getValue());
            if(expValue > 1000){
                return expValue;
            }else{
                return 0;
            }
        }catch(NumberFormatException e){
            out.print(StatusConfig.APP_DESC+" TimerValidator failed due to "+ e.getLocalizedMessage());
            return 0;
        }
    }
    
    private long yearExpression(){
       try{
            expressionLogger();
            long expValue = Long.valueOf(mFrequency.getValue());
            if(expValue > 1000){
                return expValue;
            }else{
                return 0;
            }
        }catch(NumberFormatException e){
            out.print(StatusConfig.APP_DESC+" TimerValidator failed due to"+ e.getLocalizedMessage());
            return 0;
        }
    }
    
    private void expressionLogger(){
        try{
            DateFormat dFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            out.print(StatusConfig.APP_DESC+" TimerValidator creating "+mFrequency.getKey()+" ScheduleExpression: "+ mFrequency.getValue()+
                    " Which Expires "+ dFormat.format(expires));
        }catch(Exception e){
            out.print(StatusConfig.APP_DESC+" TimerValidator Expression Logger failed due to "+ e.getLocalizedMessage());
        }
    }
}

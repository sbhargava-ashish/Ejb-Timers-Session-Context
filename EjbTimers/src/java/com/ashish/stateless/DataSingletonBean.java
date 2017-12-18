/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashish.stateless;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;

/**
 *
 * @author Ashish.b
 */
@Startup
@Singleton
public class DataSingletonBean {
    
    
    private List<String> gateways = new ArrayList<>();

    @Resource
    private SessionContext sessionContext;
    
    public List<String> getGateways() {
        return gateways;
    }

    @Override
    public String toString() {
        return "DataSingletonBean{" + "gateways=" + gateways + '}';
    }

    public void setGateways(List<String> gateways) {
        this.gateways = gateways;
    }
    
    
    @Schedule(second = "0" , minute ="0" , hour = "*", persistent=false) 
    public void runHour(Timer timer) {
        System.out.println(new Date() + " -- Hour timer");
    }
    
    
    @Schedule(second = "0" , minute ="0" , hour = "16" , persistent=false) 
    public void runDay(Timer timer) {
        System.out.println(new Date()  + " -- Day timer");
    }
    
    @PostConstruct
    public void runStartUp() {
        cancelExisting();
    	ScheduleExpression hourExpression = new ScheduleExpression();
	TimerConfig hourTimerConfig = new TimerConfig();
	hourTimerConfig.setInfo("Test");
	hourExpression.year("*").month("*").dayOfMonth("*").hour("*").minute("*/2").second("10");
	sessionContext.getTimerService().createCalendarTimer(hourExpression,hourTimerConfig );
    }

    private void cancelExisting() {
       try {
	         Collection<Timer> allTimers = sessionContext.getTimerService().getTimers();
	         
	         for (Timer timer : allTimers) {
	             Serializable info = timer.getInfo();
	             if(info != null) {
	                if(info.equals("Test") ) {
	                    timer.cancel();
	                }
	             }
	        }
	        } catch(Exception ex) {
	        	// Timers are Expired or canceled already
	        }
    }
    @Timeout
    public void Timeout(Timer timer) {
        if (timer.getInfo().equals("Test")) {
            System.out.println("Date" + new Timestamp(System.currentTimeMillis()));
        } 
    }
   
}

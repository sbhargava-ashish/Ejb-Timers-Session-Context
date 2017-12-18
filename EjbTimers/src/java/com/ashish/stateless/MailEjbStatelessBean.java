/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashish.stateless;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;


@Stateless
public class MailEjbStatelessBean implements MailEjbStatelessBeanLocal {

    @Resource
    private SessionContext context;

//    @EJB
//    private DataSingletonBean dataSingletonBean;
    
    private String hour_service = "Hours service";
       
    @Override
    public void printMailId(String mailId ) {
        System.out.println("Mail id is " + mailId);
    }
    
    @Override
    public void createTimers() {
       TimerConfig timerConfig = new TimerConfig();
       timerConfig.setInfo("MinuteTimer");
    
       TimerConfig timerConfig1 = new TimerConfig();
       timerConfig1.setInfo("3MinuteTimer");
       cancelExistingTimers();
       ScheduleExpression se = new ScheduleExpression();
//       se.year("*").month("*").dayOfMonth("*").hour("*").minute("*").second("*/30");
        se.year("*").month("*").dayOfMonth("*").hour("*").minute("*/15");
//       se.year("*").month("*").dayOfMonth("*").hour("16").minute(0).second(0);
       context.getTimerService().createCalendarTimer(se,timerConfig );
//       context.getTimerService().createIntervalTimer(0L,10000L,timerConfig1 );
    }
    
    
    @Override
    public void createTimer(long duration) {
       context.getTimerService().createTimer(duration, "Hello World!");
    }
    
    @Override
    public void createTimer() {
       UserData userData = new UserData();
       userData.setUserName(hour_service);
       TimerConfig timerConfig = new TimerConfig();
       timerConfig.setInfo(userData);
//       timerConfig.setPersistent(true);
       context.getTimerService().createIntervalTimer( 1000L, 3000L , timerConfig);
    }
    
    @Override
    public void destroy() {
        Collection<Timer> allTimers = context.getTimerService().getAllTimers();
        for (Timer time : allTimers) {
            UserData timeInfo = (UserData) time.getInfo();
            if (timeInfo.getUserName().equalsIgnoreCase(hour_service)) {
                time.cancel();
            }
        }
    }

//    @Timeout
    public void timeOutHandler(Timer timer) throws javax.ejb.NoSuchObjectLocalException{
        
       
       UserData info = (UserData) timer.getInfo();
       System.err.println("info service name  " + info.getUserName());
       int count = 0 ;
        Collection<Timer> allTimers = context.getTimerService().getAllTimers();
        for (Timer time : allTimers) {
            count++;
//            time.cancel();
        }
        System.out.println("count" + count );
    }
    
//    @Schedule(hour = "*", minute = "*", second = "*/2")
//    protected void init(Timer timer)
//    {
//      System.out.println("count---------------------------------" );
//
//       timer.cancel();
//    }
    
    @Override
    public void start() {
        System.out.println("com.ashish.stateless.MailEjbStatelessBean.start()");
        Collection<Timer> allTimers = context.getTimerService().getAllTimers();
        for (Timer allTimer : allTimers) {
            allTimer.getInfo().equals("Test");
            System.out.println("-----------------------------------------------------");
            allTimer.cancel();
        }
        
        context.getTimerService().createTimer(1000L, 3000L, hour_service);
    }
    
    @Override
    public void stop() {
        System.out.println("com.ashish.stateless.MailEjbStatelessBean.start()");
        Collection<Timer> allTimers = context.getTimerService().getAllTimers();
        for (Timer allTimer : allTimers) {
            allTimer.getInfo().equals("Test");
//            allTimer.cancel();
        }
    }
    
    @Timeout
    public void time(Timer timer) throws javax.ejb.NoSuchObjectLocalException{
        System.out.println("timer" + timer.getInfo());
        System.out.println("count-----" );
    }

    private void cancelExistingTimers() {
        try {
         Collection<Timer> allTimers = context.getTimerService().getAllTimers();
         
         for (Timer timer : allTimers) {
             Serializable info = timer.getInfo();
             if(info != null) {
                if(info.equals("3MinuteTimer")) {
                    timer.cancel();
                }
                if(info.equals("MinuteTimer")) {
                    timer.cancel();
                }
             }
            
        }
        } catch(Exception ex) {
            
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashish.stateless;

import javax.ejb.Local;

@Local
public interface MailEjbStatelessBeanLocal {
 
    public void printMailId(String mailId);
    
     public void createTimer(long duration);
     
     public void createTimer();
     
     public void destroy() ;
     
     public void start();
     
     public void stop();
     
     public void createTimers();
             
             
}

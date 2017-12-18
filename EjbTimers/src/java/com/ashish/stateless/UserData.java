/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashish.stateless;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserData implements Serializable{

        public UserData() {
        }
        
        private String userName;

    @Override
    public String toString() {
        return "UserData{" + "userName=" + userName + ", gatewaysIds=" + gatewaysIds + '}';
    }
        
        private List<String> gatewaysIds = new ArrayList<>();

    public void setGatewaysIds(List<String> gatewaysIds) {
        this.gatewaysIds = gatewaysIds;
    }

        public List <String> getGatewaysIds() {
            return gatewaysIds;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }
    }

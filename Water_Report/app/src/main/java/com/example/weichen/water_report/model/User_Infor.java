package com.example.weichen.water_report.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by weichen on 2/10/17.
 */

public class User_Infor {

    /*
    this valuse is using to store all of user data.
     */
    private Map<String, String> _users = new HashMap<>();

    /*
    user that is using app right now.
     */
    private String currentUse;

    public String getCurrentUse() {
        return currentUse;
    }

    public void addUser(String username, String password) {
        _users.put(username, password);
    }
    

}

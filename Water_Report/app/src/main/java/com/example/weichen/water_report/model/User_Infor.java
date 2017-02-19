package com.example.weichen.water_report.model;

import android.os.StrictMode;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * Created by weichen on 2/10/17.
 */

public class User_Infor {

    /**
     * This is hashmap to store user information
     */
    private Map<String, String > _users = new HashMap<>();

    /**
     * to store the current user
     */
    private String currentUser;


    /**
     * the default Constructs
     */
    public User_Infor() {
        _users.put("user","pass");
    }

    /**
     * Constructs chaining
     * @param userName the username input
     * @param passWord the password input
     */
    public User_Infor(String userName, String passWord) {
        this();
        _users.put(userName,passWord);
    }


    /**
     * use to checking that the username and password
     * @param userName the input of username
     * @param passWord the input of password
     * @return return Ture if username exits and match to the password and set currentUser to
     *         userName, otherwise return flase
     */
    public boolean checkLogin(String userName, String passWord) {
        if (_users.containsKey(userName)) {
            if (_users.containsValue(passWord)) {
                currentUser = userName;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * getting the current username
     * @return the current username
     */
    public String getCurrentUse() {
        return currentUser;
    }


    /**
     * using to register a new user
     * using to register a new user
     * @param username the username
     * @param password the password
     */
    public void addUser(String username, String password) {
        _users.put(username, password);
    }


}

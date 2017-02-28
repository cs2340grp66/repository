package com.example.weichen.water_report.model;

import android.os.StrictMode;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * Created by weichen on 2/10/17.
 */

public class User_Infor implements Serializable {

    public String userName;
    public String phoneNum;
    public String address;
    public String classes;

    /**
     * the default constructor
     */
    public User_Infor(){};


    /**
     * constructor for User_infor class
     * @param userName
     * @param phoneNum
     * @param address
     */
    public User_Infor(String userName, String phoneNum, String address, String classes) {
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.classes = classes;
    }





}

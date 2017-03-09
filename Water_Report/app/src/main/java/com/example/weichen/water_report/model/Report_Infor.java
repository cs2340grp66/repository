package com.example.weichen.water_report.model;

/**
 * Created by weichen on 3/8/17.
 */

public class Report_Infor {

    public String location;
    public String type;
    public String condition;
    public String repoterName;
    public String reportNum;
    public String date;

    /**
     *
     * the default constructor
     */
    public Report_Infor() { };


    /**
     *
     * the constructor chaining
     */
    public Report_Infor(String location, String type, String condition, String date, String reportNum, String repoterName) {
        this.location = location;
        this.type = type;
        this.condition = condition;
        this.date = date;
        this.reportNum = reportNum;
        this.repoterName = repoterName;
    }

    /**
     *
     * the setter for userName
     */
    public void setRepoterName(String repoterName) {
        this.repoterName = repoterName;
    }

    /**
     *
     * the setter for reportNum
     */
    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }



}

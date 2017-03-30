package com.example.weichen.water_report.model;

/**
 * Created by weichen on 3/28/17.
 */

public class Quality_Report {
    public String date;
    public String reportNum;
    public String reporter;
    public String locations;
    public String conditions;
    public String virus_PPM;
    public String contaminant_PPM;


    /**
     * the default constructor
     */
    public Quality_Report() { };

    /**
     *
     * the constructor chaining
     */
    public Quality_Report(String date, String reportNum, String reporter, String locations,
                            String conditions,String virus_PPM, String contaminant_PPM ) {
        this.date = date;
        this.reportNum = reportNum;
        this.reporter = reporter;
        this.locations = locations;
        this.conditions = conditions;
        this.virus_PPM = virus_PPM;
        this.contaminant_PPM = contaminant_PPM;

    }

    /**
     *
     * the setter for userName
     * @param repoterName name of repoter
     */
    public void setRepoterName(String repoterName) {
        this.reporter = repoterName;
    }

    /**
     *
     * the setter for reportNum
     * @para reportNum number of repoter
     */
    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }
}

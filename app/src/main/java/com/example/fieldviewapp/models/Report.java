package com.example.fieldviewapp.models;

public class Report {
    private int reportId;
    private String reportName;
    private String reportDate;
    private String reportDetails;

    // Constructor
    public Report(int reportId, String reportName, String reportDate, String reportDetails) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.reportDate = reportDate;
        this.reportDetails = reportDetails;
    }

    // Getter and Setter Methods
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportDetails() {
        return reportDetails;
    }

    public void setReportDetails(String reportDetails) {
        this.reportDetails = reportDetails;
    }
}

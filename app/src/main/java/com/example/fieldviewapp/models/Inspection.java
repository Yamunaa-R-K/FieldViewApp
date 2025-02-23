package com.example.fieldviewapp.models;

public class Inspection {
    private int inspectionId;
    private String inspectionName;
    private String inspectionDate;
    private String inspectionStatus;

    // Constructor
    public Inspection(int inspectionId, String inspectionName, String inspectionDate, String inspectionStatus) {
        this.inspectionId = inspectionId;
        this.inspectionName = inspectionName;
        this.inspectionDate = inspectionDate;
        this.inspectionStatus = inspectionStatus;
    }

    // Getter and Setter Methods
    public int getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(int inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(String inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }
}

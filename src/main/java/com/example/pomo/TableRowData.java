package com.example.pomo;

public class TableRowData {
    private String lengthOfSession;
    private String currentDateTime;
    private String status;

    public TableRowData(String lengthOfSession, String currentDateTime, String status) {
        this.lengthOfSession = lengthOfSession;
        this.currentDateTime = currentDateTime;
        this.status = status;
    }

    public String getLengthOfSession() {
        return lengthOfSession;
    }

    public void setLengthOfSession(String lengthOfSession) {
        this.lengthOfSession = lengthOfSession;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(String currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

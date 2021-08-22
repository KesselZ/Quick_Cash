package com.example.project1;

public class TaskStatus {

    static final String PUBLISHED = "Published";
    static final String ACCEPTED = "Accepted";
    static final String UNPAID = "Unpaid";
    static final String FINISHED = "Finished";


    private String status;


    public TaskStatus(){
        this.status=PUBLISHED;
    }

    public String getStatus() {
        return status;
    }


    /**
     * set TaskStatus to Accepted
     */

    public void setStatusAccepted() {
        this.status = ACCEPTED;
    }
    /**
     * set TaskStatus to Unpaid
     */
    public void setStatusUnpaid() {
        this.status = UNPAID;
    }

    /**
     * set TaskStatus to Finished
     */

    public void setStatusFinished() {
        this.status =FINISHED;
    }

    public void setStatus(String s){
        this.status=s;
    }



}

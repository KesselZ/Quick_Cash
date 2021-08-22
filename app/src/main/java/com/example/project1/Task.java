package com.example.project1;

import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

public class Task implements Serializable {

    static final String NOWORKER = "Does Not Be Accepted Yet";

    private String taskId;
    private String title;
    private String description;
    private Date workDate;
    private Date postDate;
    private int wage;
    private String publisher;
    private String worker;
    private String status;
    private TaskLocation location;
    private String address;


    public Task() {

    }

    public Task(String title, String description, Date workDate, int wage, String publisher,String address,TaskLocation taskLocation) {
        this.taskId = java.util.UUID.randomUUID().toString();
        this.workDate = workDate;
        this.title = title;
        this.description = description;
        this.postDate = new Date();
        this.wage = wage;
        this.publisher = publisher;
        this.status = TaskStatus.PUBLISHED;
        this.worker = Task.NOWORKER;
        this.location=taskLocation;
        this.address=address;

    }

    /**
     *
     * @param worker the userName of worker who accepts this task
     */
    public void acceptTask(String worker) {
        this.worker = worker;
        this.status = TaskStatus.ACCEPTED;

    }

    /**
     * if task has been accepted or expired, then it is not available to user
     * @return if this task is available for user to see
     */
    public boolean available() {
        return this.status.equals(TaskStatus.PUBLISHED) && workDate.after(new Date());
    }

    /**
     *
     * @return the workDate in "yyyy-mm-dd" format
     */
    public String formattedWorkDate() {

        return DateProcessor.dateToString(workDate).substring(0, 10);
    }

    //getters and setters
    public String getTitle() {
        return title;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getWorkDate() {
        return DateProcessor.dateToString(workDate);

    }

    public void setWorkDate(String workDate) throws ParseException {

        this.workDate = DateProcessor.stringToDate(workDate);
    }

    public String getPostDate() {


        return DateProcessor.dateToString(postDate);

    }

    public void setPostDate(String postDate) throws ParseException {

        this.postDate = DateProcessor.stringToDate(postDate);
    }

    public String getWorker() {
        return worker;
    }

    public String getStatus() {
        return status;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public int getWage() {
        return wage;
    }

    public void setTaskId(String id) {
        this.taskId = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TaskLocation getLocation() {
        return location;
    }

    public void setLocation(TaskLocation location) {
        this.location = location;
    }

    /**
     * implement compare function for task according to date
     */

    public static Comparator<Task> postDateSort = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {

            // if task 1 is posted before t2
            if (t1.postDate.compareTo(t2.postDate) > 0) {
                return 1;
            } else {
                return -1;
            }
        }
    };


}

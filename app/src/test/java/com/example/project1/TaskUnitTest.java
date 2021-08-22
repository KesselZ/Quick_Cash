package com.example.project1;

import android.location.Address;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TaskUnitTest {
     static Task t;
     static Date workDate;
     static Date currentDate;
 @BeforeClass
public static void setup(){
    currentDate=new Date();
    workDate=new Date();
    workDate.setMonth(6);
    t=new Task("title","description",workDate,50,"publisher","",new TaskLocation(0,0));
}

        @Test
        public void testTitle() {
           assertEquals("title",t.getTitle());

        }

    @Test
    public void testDescription() {
        assertEquals("description",t.getDescription());
    }

    @Test
    public void testWage() {

        assertEquals(50,t.getWage());
    }

    @Test
    public void testWorkDate() {
        String desc = String.format("%d-%2d-%2d", workDate.getYear()+1900, (workDate.getMonth()+1), workDate.getDate()).replace(" ", "0");

        assertEquals(desc,t.formattedWorkDate());
    }

    @Test
    public void testPublisher() {
        assertEquals("publisher",t.getPublisher());
    }

    @Test
    public void testTaskID() {
        assertNotNull(t.getTaskId());
    }

    @Test
    public void testStatus() {
        assertEquals(TaskStatus.PUBLISHED,t.getStatus());
    }
    @Test
    public void testAcceptTask() {
        Task task=new Task("title","description",workDate,50,"publisher","",new TaskLocation(0,0));
        String worker="Bob";
        task.acceptTask(worker);
        assertEquals(TaskStatus.ACCEPTED,task.getStatus());
        assertEquals(worker,task.getWorker());
    }

    @Test
    public void testAvailable() {
        assertTrue(t.available());
    }

    @Test
    public void testAcceptedTask() {
        Task task=new Task("title","description",workDate,50,"publisher","",new TaskLocation(0,0));
        task.acceptTask("BOB");
        assertFalse(task.available());
    }

    @Test
    public void testPastDueTask() {
        Date pastDate=new Date();
        pastDate.setYear(100); //set year to 2000
        Task pastTask=new Task("title","description",pastDate,50,"publisher","",new TaskLocation(0,0));
        assertFalse(pastTask.available());
    }

    @Test
    public void testPostDate() {

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");

        assertEquals(ft.format(currentDate).substring(0,16),t.getPostDate().substring(0,16));
    }



}

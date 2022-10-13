/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Dell
 */
public class Session {

    /*
    SELECT TOP (1000) [groupId]
      ,[timeSlot]
      ,[date]
      ,[room]
      ,[lectureCode]
      ,[num]
      ,[sessionid]
  FROM [FAP_AttendenceChecking].[dbo].[Session]
    */
    private int sessionid;
    private int groupId;
    private int timeSlot;
    private Date date;
    private String room;
    private String lectureCode;
    private int num;

    public Session() {
    }

    public Session(int sessionid, int groupId, int timeSlot, Date date, String room, String lectureCode, int num) {
        this.sessionid = sessionid;
        this.groupId = groupId;
        this.timeSlot = timeSlot;
        this.date = date;
        this.room = room;
        this.lectureCode = lectureCode;
        this.num = num;
    }

    public int getSessionid() {
        return sessionid;
    }

    public void setSessionid(int sessionid) {
        this.sessionid = sessionid;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int group) {
        this.groupId = group;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLecture() {
        return lectureCode;
    }

    public void setLecture(String lecture) {
        this.lectureCode = lecture;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class Group {
    /*
SELECT TOP (1000) [groupId]
      ,[groupName]
      ,[subjectCode]
      ,[lectureCode]
  FROM [FAP_AttendenceChecking].[dbo].[Group]
    */
    private int groupId;
    private String groupName;
    private Subject subject;
    private Lecture lecture;
    private ArrayList<Student> students;

    public Group() {
    }

    public Group(int groupId, String groupName, Subject subject, Lecture lecture, ArrayList<Student> students) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.subject = subject;
        this.lecture = lecture;
        this.students = students;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
    
    
}

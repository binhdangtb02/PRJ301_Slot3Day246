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
public class Lecture {
    /*
    SELECT TOP (1000) [lectureCode]
      ,[lectureName]
      ,[dob]
      ,[gender]
      ,[email]
      ,[image]
  FROM [FAP_AttendenceChecking].[dbo].[Lecture]
    */
    private String lectureCode;
    private Date dob;
    private boolean gender;
    private String email;
    private String image;

    public Lecture() {
    }

    public Lecture(String lectureCode, Date dob, boolean gender, String email, String image) {
        this.lectureCode = lectureCode;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.image = image;
    }

    public String getLectureCode() {
        return lectureCode;
    }

    public void setLectureCode(String lectureCode) {
        this.lectureCode = lectureCode;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
}

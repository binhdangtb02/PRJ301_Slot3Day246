/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dell
 */
public class Attendence {
    private Session session;
    private String studentId;
    private int status;

    public Attendence() {
    }

    public Attendence(Session session, String studentId, int status) {
        this.session = session;
        this.studentId = studentId;
        this.status = status;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getStudent() {
        return studentId;
    }

    public void setStudent(String student) {
        this.studentId = student;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}

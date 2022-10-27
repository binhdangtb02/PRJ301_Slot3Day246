/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import helper.DateTimeHelper;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attendence;
import model.Group;
import model.Lecture;
import model.Session;
import model.Student;
import model.Subject;

/**
 *
 * @author Dell
 */
public class StudentDAO extends DBContext {

    public Student getStudentById(String studentId) {
        String sql = "SELECT * FROM Student where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, studentId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Student s = new Student();
                s.setId(studentId);
                s.setDob(rs.getDate("dob"));
                s.setEmail(rs.getString("Email"));
                s.setGender(rs.getBoolean("gender"));
                s.setImage(rs.getString("image"));
                s.setName(rs.getString("name"));
                return s;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<Student> getStudentByGroupId(String groupId) {
        ArrayList<Student> listStudent = new ArrayList<>();
        String sql_select_student = "SELECT * \n"
                + "FROM [Group] g\n"
                + "INNER JOIN StudentInGroup sig on g.groupId = sig.groupId\n"
                + "INNER JOIN Student s on s.id = sig.studentId\n"
                + "where g.groupId = ?";
        try {
            PreparedStatement st_select_student = connection.prepareStatement(sql_select_student);
            st_select_student.setString(1, groupId);
            ResultSet rs = st_select_student.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getString("id"));
                s.setEmail(rs.getString("email"));
                s.setName(rs.getString("name"));
                listStudent.add(s);
            }
            return listStudent;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }



   

  


   
}

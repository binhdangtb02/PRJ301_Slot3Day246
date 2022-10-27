/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
public class TeacherDAO extends DBContext {


    public Lecture getLectureById(String lectureCode) {
        String sql = "SELECT * FROM Lecture where lectureCode = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, lectureCode);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Lecture lecture = new Lecture();
                lecture.setDob(rs.getDate("dob"));
                lecture.setEmail(rs.getString("email"));
                lecture.setGender(rs.getBoolean("gender"));
                lecture.setImage(rs.getString("image"));
                lecture.setLectureCode(rs.getString("lectureCode"));
                lecture.setLectureName(rs.getString("lectureName"));
                return lecture;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

   

  

  

    
    
    
}

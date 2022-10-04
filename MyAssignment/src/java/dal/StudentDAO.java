/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Student;

/**
 *
 * @author Dell
 */
public class StudentDAO extends DBContext {

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM Student where id = ? ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Student s = new Student(rs.getInt("id"), rs.getString("name"), rs.getBoolean("gender"), rs.getDate("dob"));
                return s;
            }
        } catch (SQLException e) {

        }
        return null;
    }
}

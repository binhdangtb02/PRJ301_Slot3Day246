/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Student;
import model.Teacher;

/**
 *
 * @author Dell
 */
public class TeacherDAO extends DBContext{
    public Teacher getTeacherById(int id) {
        String sql = "SELECT * FROM Teacher where id = ? ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Teacher t = new Teacher(rs.getInt("id"), rs.getString("name"), rs.getBoolean("gender"), rs.getDate("dob"));
                return t;
            }
        } catch (SQLException e) {

        }
        return null;
    }
}

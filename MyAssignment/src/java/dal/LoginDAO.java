/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Account;

/**
 *
 * @author Dell
 */
public class LoginDAO extends DBContext {

    public Account getAccount(String user, String pass) {
        String sql = "SELECT * FROM Account WHERE [user]= ? and pass= ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, user);
            st.setString(2, pass);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
               
                int teacherId = rs.getInt("teacherID");
                int studentId = rs.getInt("studentID");
                Account a = new Account(user, pass, teacherId, studentId);
                return a;
            }
        } catch (SQLException e) {

        }
        return null;
    }
    public static void main(String[] args) {
        LoginDAO logindao = new LoginDAO();
        System.out.println(logindao.getAccount("1", "1"));
    }
}

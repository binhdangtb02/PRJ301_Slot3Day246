/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attendence;

/**
 *
 * @author Dell
 */
public class EditDAO extends DBContext {

    public void insertAttendence(String sessionid, ArrayList<Attendence> listAttendence) {
        try {
            connection.setAutoCommit(false);
            String sql_update_session = "UPDATE Session set status = 1 where sessionid = ?";
            PreparedStatement st_update_session = connection.prepareStatement(sql_update_session);
            st_update_session.setString(1, sessionid);
            st_update_session.executeUpdate();
            String sql_delete_attendence = "DELETE FROM Attendence where sessionid = ?";
            PreparedStatement st_delete_session = connection.prepareStatement(sql_delete_attendence);
            st_delete_session.setString(1, sessionid);
            st_delete_session.executeUpdate();
            for (Attendence attendence : listAttendence) {
                String sql_insert_attendence = "INSERT INTO [Attendence]\n"
                        + "           ([sessionId]\n"
                        + "           ,[studentId]\n"
                        + "           ,[status]\n"
                        + "           ,[description])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?)";
                PreparedStatement st_insert_attendence = connection.prepareStatement(sql_insert_attendence);
                st_insert_attendence.setInt(1, attendence.getSession().getSessionid());
                st_insert_attendence.setString(2, attendence.getStudent().getId());
                st_insert_attendence.setInt(3, attendence.getStatus());
                st_insert_attendence.setString(4, attendence.getDescription());
                st_insert_attendence.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {

            }
            System.out.println(ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
}

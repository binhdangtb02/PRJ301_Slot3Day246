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
import model.Session;

/**
 *
 * @author Dell
 */
public class StudentDAO extends DBContext {

    public ArrayList<Attendence> getWeeklyTimetable(String studentId, String from, String to) {
        ArrayList<Attendence> listAttendence = new ArrayList<>();
    
        try {
            String sql = " SELECT distinct *\n"
                    + "  FROM Attendence a INNER JOIN [Session] s on a.sessionId =  s.sessionid\n"
                    + "  WHERE s.date >= ? AND s.date <= ? AND a.studentId = ? \n"
                    + "  order by s.date  asc";
            PreparedStatement st = connection.prepareStatement(sql);    
            
            st.setDate(1, DateTimeHelper.getDate(from));
            st.setDate(2, DateTimeHelper.getDate(to));
            st.setString(3, studentId);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int groupId = rs.getInt("groupId");
                int timeSlot = rs.getInt("timeSlot");
                Date date = rs.getDate("date");
                String room = rs.getString("room");
                String lectureCode = rs.getString("lectureCode");
                int num =  rs.getInt("num");
                int sessionid =  rs.getInt("sessionid");
                Session s = new Session(sessionid, groupId, timeSlot, date, room, lectureCode, num);
                Attendence a = new Attendence(s, rs.getString("studentId"), rs.getBoolean("status"));
                listAttendence.add(a);
            }
            return listAttendence;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    public static void main(String[] args) {
        System.out.println(new StudentDAO().getWeeklyTimetable("HE160114", "2022-10-10" , "2022-10-16").get(0).getSession().getDate().compareTo(Date.valueOf(LocalDate.now())));
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Group;
import model.Session;

/**
 *
 * @author Dell
 */
public class SessionDAO extends DBContext {
    public Session getSessionByLectureCodeAndSessionId(String lectureCode, String sessionid){
        String sql_select_session = "SELECT * FROM Session where sessionid = ?  and lectureCode = ?";
        try{
            PreparedStatement st_select_session =  connection.prepareStatement(sql_select_session);
            st_select_session.setString(1, sessionid);
            st_select_session.setString(2, lectureCode);
            ResultSet rs_select_session = st_select_session.executeQuery();
            if(rs_select_session.next()){
                Session session = new Session();
                session.setSessionid(rs_select_session.getInt("sessionid"));
                return session;
            }
            
        }catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
    public ArrayList<Session> getSesByGroupIdAndLectureCode(String groupId, String lectureCode) {
        ArrayList<Session> sessions = new ArrayList<>();
        String sql = "SELECT g.groupId, g.groupName, g.subjectCode, g.lectureCode,s.sessionid ,s.timeSlot, s.date, s.room, s.num, ISNULL(s.[status],0) [status]\n"
                + "FROM [Group] g INNER JOIN [Session] s on g.groupId =  s.groupId\n"
                + "  WHERE g.groupId = ? and s.lectureCode =? \n"
                + "	order by  date";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, groupId);
            st.setString(2, lectureCode);
            ResultSet rs = st.executeQuery();
            GroupDAO grDAO = new GroupDAO();
            while (rs.next()) {
                Session session = new Session();
                Group group = grDAO.getGroup(rs.getInt("groupId"));

                session.setDate(rs.getDate("date"));
                session.setGroup(group);
                session.setNum(rs.getInt("num"));
                session.setRoom(rs.getString("room"));
                session.setSessionid(rs.getInt("sessionId"));
                session.setTimeSlot(rs.getInt("timeSlot"));
                session.setStatus(rs.getBoolean("status"));
                sessions.add(session);
            }
            return sessions;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Session getSessionBySesId(String sessionid) {
        String sql = " SELECT s.groupId, s.timeSlot, s.[date], s.room, s.lectureCode, s.num, s.sessionid, "
                + " ISNULL(s.status, 0) [status], g.groupName, g.subjectCode, g.lectureCode\n"
                + "  FROM [Session] s INNER JOIN [Group] g on s.groupId = g.groupId"
                + "  WHERE s.sessionid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, sessionid);
            ResultSet rs = st.executeQuery();
            GroupDAO grDAO = new GroupDAO();
            if (rs.next()) {
                Session session = new Session();
                session.setDate(rs.getDate("date"));
                session.setRoom(rs.getString("room"));
                session.setNum(rs.getInt("num"));
                session.setLectureCode(rs.getString("lectureCode"));
                session.setTimeSlot(rs.getInt("timeSlot"));
                session.setStatus(rs.getBoolean("status"));
                session.setSessionid(rs.getInt("sessionid"));
                Group group = grDAO.getGroup(rs.getInt("groupId"));
                session.setGroup(group);
                return session;
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public ArrayList<Session> getSessionByGroupId(String groupId) {
        ArrayList<Session> sessions = new ArrayList<>();
        String sql_select_session = "SELECT g.groupId, g.groupName, g.subjectCode, g.lectureCode,s.sessionid ,s.timeSlot, s.date, s.room, s.num, ISNULL(s.[status],0) [status]\n"
                + "FROM [Group] g INNER JOIN [Session] s on g.groupId =  s.groupId\n"
                + " WHERE g.groupId = ? order by [date] ";
        try {
            PreparedStatement st_select_session = connection.prepareStatement(sql_select_session);
            st_select_session.setString(1, groupId);
            ResultSet rs = st_select_session.executeQuery();
            GroupDAO grDAO = new GroupDAO();
            while (rs.next()) {
                Session session = new Session();
               
                Group group = grDAO.getGroup(rs.getInt("groupId"));
                session.setDate(rs.getDate("date"));
                session.setGroup(group);
                session.setNum(rs.getInt("num"));
                session.setRoom(rs.getString("room"));
                session.setSessionid(rs.getInt("sessionId"));
                session.setTimeSlot(rs.getInt("timeSlot"));
                session.setStatus(rs.getBoolean("status"));
                sessions.add(session);
            }
            return sessions;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    public static void main(String[] args) {
        SessionDAO sessionDAO = new SessionDAO();
        System.out.println(sessionDAO.getSessionByLectureCodeAndSessionId("sonnt", "1"));
    }
}

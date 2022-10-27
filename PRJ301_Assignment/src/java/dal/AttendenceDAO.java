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
import java.util.ArrayList;
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
public class AttendenceDAO extends DBContext {

    public ArrayList<Attendence> getWeeklyTimetable(String studentId, String from, String to) {
        ArrayList<Attendence> listAttendence = new ArrayList<>();

        try {
//            String sql = " SELECT distinct *\n"
//                    + "  FROM Attendence a INNER JOIN [Session] s on a.sessionId =  s.sessionid\n"
//                    + "  WHERE s.date >= ? AND s.date <= ? AND a.studentId = ? \n"
//                    + "  order by s.date  asc";

            String sql = "  SELECT   s.id,g.groupId, ses.timeSlot, ses.[date], ses.room, ses.lectureCode, ses.num, ses.sessionid, ISNULL(a.status,-1) status \n"
                    + "  FROM Student s INNER JOIN StudentInGroup sg on s.id = sg.studentId\n"
                    + "  INNER JOIN [Group] g on g.groupId = sg.groupId\n"
                    + "  INNER JOIN [Session] ses on ses.groupId = g.groupId \n"
                    + "  LEFT JOIN Attendence a on a.sessionId = ses.sessionid\n"
                    + "  where  s.id = ? and (a.studentId = ? OR a.studentId IS NULL) "
                    + " AND ses.date >= ? AND ses.date <= ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, studentId);
            st.setString(2, studentId);
            st.setDate(3, DateTimeHelper.getDate(from));
            st.setDate(4, DateTimeHelper.getDate(to));

            ResultSet rs = st.executeQuery();
            GroupDAO grDAO = new GroupDAO();
            while (rs.next()) {
                
                Group group = grDAO.getGroup(rs.getInt("groupId"));
                int timeSlot = rs.getInt("timeSlot");
                Date date = rs.getDate("date");
                String room = rs.getString("room");
                String lectureCode = rs.getString("lectureCode");
                int num = rs.getInt("num");
                int sessionid = rs.getInt("sessionid");
                Session s = new Session(sessionid, group, timeSlot, date, room, lectureCode, num);
                Student student = new Student();
                student.setId(rs.getString("ID"));
                Attendence a = new Attendence(s, student, rs.getInt("status"));
                listAttendence.add(a);
            }
            return listAttendence;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    public ArrayList<Attendence> getAttendenceReport(String studentId_raw, String groupId_raw) {
        ArrayList<Attendence> listAttendence = new ArrayList<>();
//        String sql = "SELECT *\n"
//                + "FROM Attendence a\n"
//                + "INNER JOIN [Session] s ON a.sessionId = s.sessionid\n"
//                + "WHERE a.studentId = ? AND s.groupId = ?";
        String sql = "    SELECT   s.id,g.groupId, ses.timeSlot, ses.[date], ses.room, ses.lectureCode, ses.num, ses.sessionid , ISNULL(a.[status],-1) \"status\"\n"
                + "  FROM Student s LEFT JOIN StudentInGroup sg on s.id = sg.studentId\n"
                + "  INNER JOIN [Group] g on g.groupId = sg.groupId\n"
                + "  INNER JOIN [Session] ses on ses.groupId = g.groupId \n"
                + "  LEFT JOIN Attendence a on a.sessionId = ses.sessionid\n"
                + "  where  s.id = ? and g.groupId = ? and (a.studentId = ? OR a.studentId IS NULL)"
                + "  order by ses.[date]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, studentId_raw);
            st.setString(2, groupId_raw);
            st.setString(3, studentId_raw);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                GroupDAO grDAO = new GroupDAO();
                Group group = grDAO.getGroup(rs.getInt("groupId"));
                int timeSlot = rs.getInt("timeSlot");
                Date date = rs.getDate("date");
                String room = rs.getString("room");
                String lectureCode = rs.getString("lectureCode");
                int num = rs.getInt("num");
                int sessionid = rs.getInt("sessionid");
                Session s = new Session(sessionid, group, timeSlot, date, room, lectureCode, num);
                Student student = new Student();
                student.setId(rs.getString("ID"));
                Attendence a = new Attendence(s, student, rs.getInt("status"));
                listAttendence.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listAttendence;
    }
    
     public ArrayList<Attendence> getAttendenceByGroupId(String groupId) {
        ArrayList<Attendence> listAttendence = new ArrayList<>();
        String sql_get_attendence = "SELECT s.id studentId, s.[name] studentName, g.groupName, l.lectureCode, l.lectureName, su.subjectCode, su.subjectName, ses.sessionid, ses.[date],\n"
                + "                ses.room, ISNULL(ses.[status],0) present, ses.timeSlot,ses.num, ISNULL(a.[status], 0) [status], ISNULL(a.[description],'' ) [description]\n"
                + "                FROM [Group] g \n"
                + "				INNER JOIN StudentInGroup sig on g.groupId = sig.groupId\n"
                + "				INNER JOIN Student  s on s.id = sig.studentId\n"
                + "                INNER JOIN Lecture l on g.lectureCode =  l.lectureCode\n"
                + "                INNER JOIN [Subject] su on su.subjectCode = g.subjectCode\n"
                + "                INNER JOIN [Session] ses on g.groupId = ses.groupId\n"
                + "			\n"
                + "                LEFT JOIN Attendence a on a.sessionId = ses.sessionid AND a.studentId = s.id\n"
                + "                \n"
                + "                where g.groupId = ?\n"
                + "				order by studentId, [date], num asc";
        try {
            PreparedStatement st_get_attendence = connection.prepareStatement(sql_get_attendence);
            st_get_attendence.setString(1, groupId);
            ResultSet rs = st_get_attendence.executeQuery();
            while (rs.next()) {
                Attendence a = new Attendence();
                Session session = new Session();
                Student student = new Student();
                Group group = new Group();
                Lecture lecture = new Lecture();
                Subject subject = new Subject();
                lecture.setLectureCode(rs.getString("lectureCode"));
                lecture.setLectureName(rs.getString("lectureName"));
                subject.setSubjectCode(rs.getString("subjectCode"));
                subject.setSubjectName(rs.getString("subjectName"));
                group.setSubject(subject);
                group.setLecture(lecture);
                session.setGroup(group);
                session.setDate(rs.getDate("date"));
                session.setTimeSlot(rs.getInt("timeSlot"));
                session.setStatus(rs.getBoolean("present"));
                session.setNum(rs.getInt("num"));
                session.setRoom(rs.getString("room"));
                session.setSessionid(rs.getInt("sessionid"));
                student.setId(rs.getString("studentId"));
                student.setName(rs.getString("studentName"));
                a.setSession(session);
                a.setStudent(student);
                a.setStatus(rs.getInt("status"));
                a.setDescription(groupId);
                listAttendence.add(a);
            }
            return listAttendence;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
     public ArrayList<Attendence> getAttendenceBySessionId(String sessionid) {
        ArrayList<Attendence> listAttendence = new ArrayList<>();
        String sql = "  SELECT stu.id, stu.name,stu.image, stu.gender,g.groupId, g.groupName, g.lectureCode, g.lectureCode, "
                + "     s.sessionid, s.room,s.date, s.num,s.timeSlot,\n"
                + "  s.status \"present\",  ISNULL(a.status, 0 ) status, ISNULL(a.description,'') \"description\"\n"
                + "  FROM [Session] s \n"
                + "  INNER JOIN [Group] g on g.groupId = s.groupId\n"
                + "  INNER JOIN StudentInGroup sg on g.groupId = sg.groupId\n"
                + "  INNER JOIN Student stu on stu.id = sg.studentId \n"
                + "  LEFT JOIN Attendence a on s.sessionid = a.sessionId and stu.id = a.studentId\n"
                + "  where s.sessionid = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, sessionid);
            ResultSet rs = st.executeQuery();
            GroupDAO grDAO = new GroupDAO();
            while (rs.next()) {
                Group group = grDAO.getGroup(rs.getInt("groupId"));
                int timeSlot = rs.getInt("timeSlot");
                Date date = rs.getDate("date");
                String room = rs.getString("room");
                String lectureCode = rs.getString("lectureCode");
                int num = rs.getInt("num");
                int sessionId = rs.getInt("sessionid");
                Session s = new Session(sessionId, group, timeSlot, date, room, lectureCode, num);
                Student student = new Student();
                student.setId(rs.getString("id"));
                student.setImage(rs.getString("image"));
                student.setName(rs.getString("name"));
                Attendence a = new Attendence(s, student, rs.getInt("status"));
                a.setDescription(rs.getString("description"));
                listAttendence.add(a);
            }
            return listAttendence;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
     
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

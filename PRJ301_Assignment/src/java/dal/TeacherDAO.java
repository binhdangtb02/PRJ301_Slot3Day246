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

    public Group getGroup(int groupId) {
        String sql = "SELECT g.groupId, g.groupName, g.subjectCode, s.id \"studentId\",s.name \"studentName\", s.gender \"studentGender\",\n"
                + "s.image \"studentImage\", s.email \"studentEmail\", s.dob \"studentDOB\",  l.lectureCode, l.lectureName, l.dob \"lectureDOB\",\n"
                + "l.gender \"lectureGender\",l.email \"lectureEmail\", l.image \"lectureImage\" , su.subjectCode, su.subjectName, su.numberOfSlots\n"
                + "FROM [Group] g INNER JOIN StudentInGroup sg \n"
                + "on g.groupId = sg.groupId \n"
                + "INNER JOIN Student s on s.id = sg.studentId\n"
                + "INNER JOIN Lecture l on l.lectureCode = g.lectureCode\n"
                + "INNER JOIN [Subject] su on g.subjectCode =  su.subjectCode\n"
                + "where g.groupId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, groupId);
            ResultSet rs = st.executeQuery();

            Group g = null;
            while (rs.next()) {
                if (g == null) {
                    g = new Group();
                    g.setGroupId(rs.getInt("groupId"));
                    g.setGroupName(rs.getString("groupName"));
                    Subject subject = new Subject();
                    subject.setSubjectCode(rs.getString("subjectCode"));
                    subject.setSubjectName(rs.getString("subjectName"));
                    subject.setNumberOfSlots(rs.getInt("numberOfSlots"));;
                    Lecture lecture = new Lecture();
                    lecture.setDob(rs.getDate("lectureDOB"));
                    lecture.setEmail(rs.getString("lectureEmail"));
                    lecture.setGender(rs.getBoolean("lectureGender"));
                    lecture.setGender(rs.getBoolean("lectureGender"));
                    lecture.setLectureCode(rs.getString("lectureCode"));
                    lecture.setLectureName(rs.getString("lectureName"));
                    g.setLecture(lecture);
                    g.setSubject(subject);

                } else {
                    Student s = new Student();
                    s.setId(rs.getString("studentId"));
                    s.setDob(rs.getDate("studentDOB"));
                    s.setGender(rs.getBoolean("studentGender"));
                    s.setEmail(rs.getString("studentEmail"));
                    s.setImage(rs.getString("studentImage"));
                    s.setName(rs.getString("studentName"));

                    g.getStudents().add(s);
                }
            }
            return g;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

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
            while (rs.next()) {
                Session session = new Session();
                Group group = getGroup(rs.getInt("groupId"));

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

    public LinkedHashMap<Group, Integer> getGroupsByLectureCode(String lectureCode) {
        LinkedHashMap<Group, Integer> groups = new LinkedHashMap<>();
        String sql = "SELECT  g.groupId,count(*) \"Count\", g.groupName, l.lectureCode, l.lectureName, l.gender, l.dob, \n"
                + "                 l.[image], su.subjectCode, su.subjectName,su.numberOfSlots\n"
                + "                 FROM Lecture l \n"
                + "				 INNER JOIN [Session] ses on ses.lectureCode =  l.lectureCode\n"
                + "                 INNER JOIN [Group] g on g.groupId = ses.groupId\n"
                + "				 INNER JOIN [Subject] su on  su.subjectCode = g.subjectCode\n"
                + "                WHERE ses.lectureCode = ?\n"
                + "				group by g.groupId, g.groupName, l.lectureCode, l.lectureName, l.gender, l.dob, \n"
                + "                 l.[image], su.subjectCode, su.subjectName,su.numberOfSlots"
                + " order by [groupName] ";
        try {
            PreparedStatement st = connection.prepareCall(sql);
            st.setString(1, lectureCode);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Group group = new Group();
                Subject subject = new Subject();
                Lecture lecture = new Lecture();
                subject.setSubjectCode(rs.getString("subjectCode"));
                subject.setSubjectName(rs.getString("subjectName"));
                lecture.setDob(rs.getDate("dob"));

                lecture.setGender(rs.getBoolean("gender"));
                lecture.setImage(rs.getString("image"));
                lecture.setLectureCode(rs.getString("lectureCode"));
                lecture.setLectureName(rs.getString("lectureName"));
                group.setLecture(lecture);
                group.setSubject(subject);
                group.setGroupId(rs.getInt("groupId"));
                group.setGroupName(rs.getString("groupName"));
                groups.put(group, rs.getInt("count"));
            }
            return groups;
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
            while (rs.next()) {
                Group group = getGroup(rs.getInt("groupId"));
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

    public Session getSessionBySesId(String sessionid) {
        String sql = " SELECT s.groupId, s.timeSlot, s.[date], s.room, s.lectureCode, s.num, s.sessionid, "
                + " ISNULL(s.status, 0) [status], g.groupName, g.subjectCode, g.lectureCode\n"
                + "  FROM [Session] s INNER JOIN [Group] g on s.groupId = g.groupId"
                + "  WHERE s.sessionid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, sessionid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Session session = new Session();
                session.setDate(rs.getDate("date"));
                session.setRoom(rs.getString("room"));
                session.setNum(rs.getInt("num"));
                session.setLectureCode(rs.getString("lectureCode"));
                session.setTimeSlot(rs.getInt("timeSlot"));
                session.setStatus(rs.getBoolean("status"));
                session.setSessionid(rs.getInt("sessionid"));
                Group group = getGroup(rs.getInt("groupId"));
                session.setGroup(group);
                return session;
            }
        } catch (SQLException e) {

        }
        return null;
    }
    
    public static void main(String[] args) {

        System.out.println(new TeacherDAO().getAttendenceBySessionId("1"));
        System.out.println(new TeacherDAO().getGroupsByLectureCode("sonnt"));
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

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
public class LectureReportDAO extends DBContext {

    public ArrayList<Group> getGroupsBySupervisor(String lectureCode) {
        ArrayList<Group> groups = new ArrayList<>();
        String sql_select_group = "SELECT * FROM [Group] g INNER JOIN Subject su on g.subjectCode =  su.subjectCode where lectureCode = ? ";
        try {
            PreparedStatement st_select_group = connection.prepareStatement(sql_select_group);
            st_select_group.setString(1, lectureCode);
            ResultSet rs = st_select_group.executeQuery();
            while (rs.next()) {
                Group group = new Group();
                group.setGroupId(rs.getInt("groupId"));
                group.setGroupName(rs.getString("groupName"));
                Lecture lecture = new Lecture();
                lecture.setLectureCode(lectureCode);
                Subject subject = new Subject();

                subject.setSubjectCode(rs.getString("subjectCode"));
                subject.setSubjectName(rs.getString("subjectName"));
                group.setLecture(lecture);
                group.setSubject(subject);
                groups.add(group);
            }
            return groups;
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
            while (rs.next()) {
                Session session = new Session();
                TeacherDAO te = new TeacherDAO();
                Group group = te.getGroup(rs.getInt("groupId"));
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

    public static void main(String[] args) {

        LectureReportDAO t = new LectureReportDAO();
        System.out.println(t.getAttendenceByGroupId("2"));
    }
}

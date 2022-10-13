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
import model.Group;
import model.Lecture;
import model.Session;
import model.Student;
import model.Subject;

/**
 *
 * @author Dell
 */
public class StudentDAO extends DBContext {

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
            while (rs.next()) {
                Group group = getGroup(rs.getInt("groupId"));
                int timeSlot = rs.getInt("timeSlot");
                Date date = rs.getDate("date");
                String room = rs.getString("room");
                String lectureCode = rs.getString("lectureCode");
                int num = rs.getInt("num");
                int sessionid = rs.getInt("sessionid");
                Session s = new Session(sessionid, group, timeSlot, date, room, lectureCode, num);
                Attendence a = new Attendence(s, rs.getString("studentId"), rs.getInt("status"));
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
        String sql = "SELECT *\n"
                + "FROM Attendence a\n"
                + "INNER JOIN [Session] s ON a.sessionId = s.sessionid\n"
                + "WHERE a.studentId = ? AND s.groupId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, studentId_raw);
            st.setString(2, groupId_raw);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Group group = getGroup(rs.getInt("groupId"));
                int timeSlot = rs.getInt("timeSlot");
                Date date = rs.getDate("date");
                String room = rs.getString("room");
                String lectureCode = rs.getString("lectureCode");
                int num = rs.getInt("num");
                int sessionid = rs.getInt("sessionid");
                Session s = new Session(sessionid, group, timeSlot, date, room, lectureCode, num);
                Attendence a = new Attendence(s, rs.getString("studentId"), rs.getInt("status"));
                listAttendence.add(a);
            }
        } catch (SQLException e) {

        }
        return listAttendence;
    }

    public static void main(String[] args) {
        System.out.println(new StudentDAO().getWeeklyTimetable("HE160114", "2022-10-10", "2022-10-16").get(0).getSession().getGroup().getSubject().getSubjectCode());
        System.out.println(new StudentDAO().getAttendenceReport("HE160110", "2"));
    }
}

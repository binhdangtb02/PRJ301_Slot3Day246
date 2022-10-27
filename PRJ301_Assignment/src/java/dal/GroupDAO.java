/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import model.Group;
import model.Lecture;
import model.Student;
import model.Subject;

/**
 *
 * @author Dell
 */
public class GroupDAO extends DBContext {

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

    public ArrayList<Group> getGroupByStudentId(String studentId) {
        ArrayList<Group> groups = new ArrayList<>();
        String sql = "select distinct ISNULL(g.groupId,-1) \"groupId\", g.groupName, g.subjectCode ,su.subjectName\n"
                + "FROM Student s\n"
                + "INNER join StudentInGroup sg on s.id = sg.studentId \n"
                + "INNER join [Group] g on g.groupId = sg.groupId\n"
                + "INNER JOIN [Subject] su on su.subjectCode = g.subjectCode \n"
                + "where s.id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, studentId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Group g = new Group();
                g.setGroupId(rs.getInt("groupId"));
                g.setGroupName(rs.getString("groupName"));
                g.setGroupId(rs.getInt("groupId"));
                Subject s = new Subject();
                s.setSubjectCode(rs.getString("subjectCode"));
                s.setSubjectName(rs.getString("subjectName"));
                g.setSubject(s);
                groups.add(g);
            }
            return groups;
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
}

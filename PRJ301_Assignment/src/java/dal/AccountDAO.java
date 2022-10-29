/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Account;
import model.Feature;
import model.Lecture;
import model.Role;
import model.Student;

/**
 *
 * @author Dell
 */
public class AccountDAO extends DBContext {

    public Account getAccount(String username, String password) {
        String sql = "SELECT a.username, a.[password],  ISNULL(a.[sid], -1 ) [sid], ISNULL(a.[lid], -1 ) [lid],\n"
                + "r.id rid, r.roleName, f.id fid, f.fname, f.furl\n"
                + "FROM Account a\n"
                + "LEFT JOIN  Account_Role ar on a.username = ar.username\n"
                + "LEFT JOIN [Role] r on  ar.rid = r.id\n"
                + "LEFT JOIN Role_Feature rf on rf.rid = r.id\n"
                + "LEFT JOIN Feature f on f.id = rf.fid\n"
                + "where a.username = ? and a.[password] = ?";
        try {
            PreparedStatement st_select_account = connection.prepareStatement(sql);
            st_select_account.setString(1, username);
            st_select_account.setString(2, password);
            ResultSet rs_select_account = st_select_account.executeQuery();
            Account account = null;
            Role currentRole = new Role();
            while (rs_select_account.next()) {
                if (account == null) {
                    account = new Account();
                    account.setUsername(username);
                    account.setPassword(password);
                    int sid, lid;
                    sid = rs_select_account.getInt("sid");
                    if (sid != -1) {
                        StudentDAO studentDAO = new StudentDAO();
                        Student student = studentDAO.getStudentById(Integer.toString(sid));
                        account.setStudent(student);
                    }
                    lid = rs_select_account.getInt("lid");
                    if (lid != -1) {
                        TeacherDAO teacherDAO = new TeacherDAO();
                        Lecture lecture = teacherDAO.getLectureById(Integer.toString(lid));
                        account.setLecture(lecture);
                    }
//                } else {
//                    int rid = rs_select_account.getInt("rid");
//                    if (rid != -1) {
//                        if (rid != currentRole.getId()) {
//                            currentRole = new Role();
//                            currentRole.setId(rs_select_account.getInt("rid"));
//                            currentRole.setRoleName(rs_select_account.getString("rname"));
//                            account.getRoles().add(currentRole);
//                        }
//                        int fid = rs_select_account.getInt("fid");
//                        if (fid != -1) {
//                            Feature feature = new Feature();
//                            feature.setId(rs_select_account.getInt("fid"));
//                            feature.setFname(rs_select_account.getString("fname"));
//                            feature.setFurl(rs_select_account.getString("furl"));
//                            currentRole.getFeatures().add(feature);
//                        }
//                    }
                }
            }
            return account;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO();
        System.out.println(accountDAO.getAccount("he160001", "2"));
    }
}

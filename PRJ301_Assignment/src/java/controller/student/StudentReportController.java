/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import controller.auth.BaseAuthorizationController;
import dal.AttendenceDAO;
import dal.GroupDAO;
import dal.StudentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Account;
import model.Attendence;
import model.Group;
import model.Student;

/**
 *
 * @author Dell
 */
public class StudentReportController extends BaseAuthorizationController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processAuthorizationGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         Account account = (Account)request.getSession().getAttribute("account");
        String studentId_raw = account.getStudent().getId();
        String index_raw = request.getParameter("index");
        int index;
        if (index_raw == null) {
            index = 0;
        } else {
            index = Integer.parseInt(index_raw);
        }
        StudentDAO stDAO = new StudentDAO();
        GroupDAO grDAO = new GroupDAO();
        AttendenceDAO attDAO = new AttendenceDAO();
        ArrayList<Group> listGroup = grDAO.getGroupByStudentId(studentId_raw);
        int numberOfAbsent = 0;
        if (listGroup != null && !listGroup.isEmpty()) {

            ArrayList<Attendence> listAttendence = attDAO.getAttendenceReport(studentId_raw, Integer.toString(listGroup.get(index).getGroupId()));
            for (Attendence a : listAttendence) {
                if (a.getStatus() == 0) {
                    numberOfAbsent++;
                }
            }
            int percenAbsent = numberOfAbsent * 100 / listAttendence.size();

            request.setAttribute("studentId", studentId_raw);
            request.setAttribute("index", index);
            request.setAttribute("listGroup", listGroup);
            request.setAttribute("listAttendence", listAttendence);
            request.setAttribute("numberOfAbsent", numberOfAbsent);
            request.setAttribute("percentageOfAbsent", percenAbsent);
        } else {
            request.setAttribute("error", "This student have not attended any course!!");
        }
        Student student = stDAO.getStudentById(studentId_raw);
        request.setAttribute("student", student);
        request.getRequestDispatcher("../view/student/attendencereport.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processAuthorizationPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

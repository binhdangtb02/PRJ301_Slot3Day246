/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecture;

import controller.auth.BaseAuthorizationController;
import dal.AttendenceDAO;
import dal.SessionDAO;
import dal.TeacherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Account;
import model.Attendence;
import model.Session;
import model.Student;

/**
 *
 * @author Dell
 */
public class EditAttendence extends BaseAuthorizationController {

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
     * @param sessionid
     * @param response servlet response
     * @return 
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public boolean checkValidSession(HttpServletRequest request, String sessionid) {
        SessionDAO sessionDAO = new SessionDAO();
        Account account = (Account) request.getSession().getAttribute("account");
        String lectureCode = account.getLecture().getLectureCode();
        Session session = sessionDAO.getSessionByLectureCodeAndSessionId(lectureCode, sessionid);
        return session != null;
    }

    @Override
    protected void processAuthorizationGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sessionid = request.getParameter("sessionid");
        if (checkValidSession(request, sessionid)) {

            TeacherDAO teDAO = new TeacherDAO();
            SessionDAO sesDAO = new SessionDAO();
            AttendenceDAO attDAO = new AttendenceDAO();
            Session session = sesDAO.getSessionBySesId(sessionid);
            ArrayList<Attendence> listAttendence = attDAO.getAttendenceBySessionId(sessionid);
            request.setAttribute("listAttendence", listAttendence);
            request.setAttribute("session", session);
            request.getRequestDispatcher("../view/lecture/editatt.jsp").forward(request, response);
        }
        else{
            response.getWriter().println("Access denied");
        }
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
        AttendenceDAO attDAO = new AttendenceDAO();
        String sessionid = request.getParameter("sessionid");
        String[] studentId = request.getParameterValues("studentid");
        ArrayList<Attendence> listAttendence = new ArrayList<>();
        for (String id : studentId) {
            Student student = new Student();
            student.setId(id);
            Session session = new Session();
            session.setSessionid(Integer.parseInt(request.getParameter("sessionid")));
            String status_raw = request.getParameter("attended?id=" + id);
            String description = request.getParameter("description?id=" + id);
            Attendence a = new Attendence();
            a.setSession(session);
            a.setStudent(student);
            a.setStatus(Integer.parseInt(status_raw));
            a.setDescription(description);
            listAttendence.add(a);
        }
        attDAO.insertAttendence(sessionid, listAttendence);
        response.sendRedirect("edit?sessionid=" + sessionid);
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

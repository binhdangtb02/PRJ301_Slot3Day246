/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecture;

import dal.LectureReportDAO;
import dal.StudentDAO;
import dal.TeacherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Attendence;
import model.Group;
import model.Lecture;
import model.Session;
import model.Student;

/**
 *
 * @author Dell
 */
public class ReportController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReportController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReportController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lectureCode = request.getParameter("lectureCode");
        LectureReportDAO lectureReportDAO =  new LectureReportDAO();
        TeacherDAO teDAO =  new TeacherDAO();
        ArrayList<Group> groups =  lectureReportDAO.getGroupsBySupervisor(lectureCode);
        Lecture lecture = teDAO.getLectureById(lectureCode);
        request.setAttribute("lecture", lecture);
        request.setAttribute("groups", groups);
        String groupId = request.getParameter("groupId");
        if(groupId != null && !groupId.isEmpty()){
            ArrayList<Session>  listSession = lectureReportDAO.getSessionByGroupId(groupId);
            ArrayList<Attendence> listAttendence =  lectureReportDAO.getAttendenceByGroupId(groupId);
            StudentDAO stDAO =  new StudentDAO();
            ArrayList<Student> listStudent = stDAO.getStudentByGroupId(groupId);
            request.setAttribute("listStudent", listStudent);
            request.setAttribute("listAttendence", listAttendence);
            request.setAttribute("listSession", listSession);
        }
        request.getRequestDispatcher("../view/teacher/report.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

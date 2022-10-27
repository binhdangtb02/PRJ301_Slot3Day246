/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecture;


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
import model.Attendence;
import model.Session;
import model.Student;

/**
 *
 * @author Dell
 */
public class EditAttendence extends HttpServlet {

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
            out.println("<title>Servlet EditAttendence</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditAttendence at " + request.getContextPath() + "</h1>");
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
        String sessionid = request.getParameter("sessionid");
        TeacherDAO teDAO = new TeacherDAO();
        SessionDAO sesDAO = new  SessionDAO();
        AttendenceDAO attDAO = new AttendenceDAO();
        Session session = sesDAO.getSessionBySesId(sessionid);
        ArrayList<Attendence> listAttendence = attDAO.getAttendenceBySessionId(sessionid);
        request.setAttribute("listAttendence", listAttendence);
        request.setAttribute("session", session);
        request.getRequestDispatcher("../view/lecture/editatt.jsp").forward(request, response);
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
        AttendenceDAO  attDAO = new AttendenceDAO();
         String sessionid =  request.getParameter("sessionid");
         String[] studentId = request.getParameterValues("studentid");
         ArrayList<Attendence> listAttendence = new ArrayList<>();
         for (String id : studentId) {
            Student student = new Student();
            student.setId(id);
            Session session = new Session();
            session.setSessionid(Integer.parseInt(request.getParameter("sessionid")));
            String status_raw = request.getParameter("attended?id="+id);
            String description = request.getParameter("description?id="+id);
            Attendence a = new Attendence();
            a.setSession(session);
            a.setStudent(student);
            a.setStatus(Integer.parseInt(status_raw));
            a.setDescription(description);
            listAttendence.add(a);
        }
         attDAO.insertAttendence(sessionid, listAttendence);
         response.sendRedirect("edit?sessionid="+sessionid);
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

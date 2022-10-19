/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import dal.StudentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Attendence;
import model.Group;
import model.Student;

/**
 *
 * @author Dell
 */
public class StudentReportController extends HttpServlet {

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
            out.println("<title>Servlet StudentReportController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentReportController at " + request.getContextPath() + "</h1>");
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
        String studentId_raw = request.getParameter("studentid");
        String index_raw = request.getParameter("index");
        int index;
        if (index_raw == null) {
            index = 0;
        } else {
            index = Integer.parseInt(index_raw);
        }
        StudentDAO stDAO = new StudentDAO();
        ArrayList<Group> listGroup = stDAO.getGroupByStudentId(studentId_raw);
        int numberOfAbsent = 0;
        if (listGroup != null && !listGroup.isEmpty()) {

            ArrayList<Attendence> listAttendence = stDAO.getAttendenceReport(studentId_raw, Integer.toString(listGroup.get(index).getGroupId()));
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

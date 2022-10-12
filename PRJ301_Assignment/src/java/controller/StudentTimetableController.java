/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.StudentDAO;
import helper.DateTimeHelper;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Attendence;

/**
 *
 * @author Dell
 */
public class StudentTimetableController extends HttpServlet {

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
            out.println("<title>Servlet StudentTimetableController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentTimetableController at " + request.getContextPath() + "</h1>");
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
        String id = request.getParameter("id");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        StudentDAO stDAO = new StudentDAO();
        LocalDate localDate = LocalDate.now();
        DayOfWeek today = localDate.getDayOfWeek();
        LocalDate startDate;
        if (from == null || to == null) {
            if (today.compareTo(DayOfWeek.MONDAY) >= 0 && today.compareTo(DayOfWeek.SATURDAY) <= 0) {
                startDate = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                from = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toString();
                to = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toString();
            } else {
                startDate = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                from = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).toString();
                to = localDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).toString();
            }
        }else{
            startDate = DateTimeHelper.getLocalDate(from);
        }
        int year = localDate.getYear();
        ArrayList<LocalDate> week  = new ArrayList<>();
        LocalDate endDate = startDate.plusDays(7);
        for(LocalDate  i = startDate; i.isBefore(endDate); i =i.plusDays(1)){
            week.add(i);
        }
        ArrayList<Date> sqlWeek  = new ArrayList<>();
     for(LocalDate  i = startDate; i.isBefore(endDate); i =i.plusDays(1)){
            sqlWeek.add(DateTimeHelper.getSqlDate(i));
        }
     
        ArrayList<Attendence> weeklyTimetable = stDAO.getWeeklyTimetable(id, from, to);
        request.setAttribute("week", week);
       request.setAttribute("sqlWeek", sqlWeek);
        request.setAttribute("weeklyTimetable", weeklyTimetable);
        request.getRequestDispatcher("../view/student/weeklytimetable.jsp").forward(request, response);
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

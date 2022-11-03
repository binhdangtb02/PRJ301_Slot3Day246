/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecture;

import controller.auth.BaseAuthorizationController;
import dal.AttendenceDAO;
import dal.SessionDAO;
import dal.StudentDAO;
import dal.TeacherDAO;
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
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import model.Account;
import model.Attendence;
import model.Lecture;
import model.Session;
import model.Student;

/**
 *
 * @author Dell
 */
public class TimetableController extends BaseAuthorizationController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void processAuthorizationGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         Account account = (Account)request.getSession().getAttribute("account");
        String id = account.getLecture().getLectureCode();
        String year_raw = request.getParameter("year");
        String week_raw = request.getParameter("week");
        TeacherDAO  teDAO  = new TeacherDAO();
        AttendenceDAO attDAO = new AttendenceDAO();
        SessionDAO sesDAO = new SessionDAO();
        LocalDate localDate = LocalDate.now();
        DayOfWeek today = localDate.getDayOfWeek();
        LocalDate startDate;
        int year, week;
        String from, to;
        // if don't have from and to date, select this week
        if (year_raw == null || week_raw == null) {
            if (today.compareTo(DayOfWeek.MONDAY) >= 0 && today.compareTo(DayOfWeek.SATURDAY) <= 0) {
                startDate = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                from = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toString();
                to = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toString();

            } else {
                startDate = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                from = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).toString();
                to = localDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).toString();

            }
            year = localDate.getYear();

        } else {
            year = Integer.parseInt(year_raw);
            week = Integer.parseInt(week_raw);
            Calendar c = Calendar.getInstance();
            c.setWeekDate(year, week, Calendar.MONDAY);
                
            startDate = DateTimeHelper.getLocalDate(c);
            from = startDate.toString();
            to = startDate.plusDays(6).toString();
        }
        
        //
        // get number of weeks in a year
        ArrayList<LocalDate> weeks = new ArrayList<>();
        Integer weeksOfYear = Calendar.getInstance().getActualMaximum(Calendar.WEEK_OF_YEAR);
        Calendar c = Calendar.getInstance();
        for (int i = 1; i <= weeksOfYear; i++) {
            c.setWeekDate(year, i, Calendar.MONDAY);
            weeks.add(DateTimeHelper.getLocalDate(c));
        }
        // caculate selected week
        ArrayList<LocalDate> selectedWeek = new ArrayList<>();
        LocalDate endDate = startDate.plusDays(7);
        for (LocalDate i = startDate; i.isBefore(endDate); i = i.plusDays(1)) {
            selectedWeek.add(i);
        }
        // convert selected week to sql date
        ArrayList<Date> sqlWeek = new ArrayList<>();
        for (LocalDate i = startDate; i.isBefore(endDate); i = i.plusDays(1)) {
            sqlWeek.add(DateTimeHelper.getSqlDate(i));
        }
        Lecture lecture = teDAO.getLectureById(id);
        ArrayList<Session> weeklyTimetable = sesDAO.getLectureWeeklyTimetable(id, from, to);
        request.setAttribute("weeksOfYear", weeksOfYear);
        request.setAttribute("weeks", weeks);
        request.setAttribute("lecture", lecture);
        request.setAttribute("year", year);
        request.setAttribute("selectedWeek", selectedWeek);
        request.setAttribute("sqlWeek", sqlWeek);
        request.setAttribute("weeklyTimetable", weeklyTimetable);
        request.getRequestDispatcher("../view/lecture/weeklytimetable.jsp").forward(request, response);
        
    }

    @Override
    protected void processAuthorizationPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}

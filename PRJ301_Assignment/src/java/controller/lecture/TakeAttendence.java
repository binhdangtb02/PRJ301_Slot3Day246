/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecture;

import controller.auth.BaseAuthorizationController;
import dal.GroupDAO;
import dal.SessionDAO;
import dal.TeacherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import model.Account;
import model.Group;
import model.Lecture;
import model.Session;

/**
 *
 * @author Dell
 */
public class TakeAttendence extends BaseAuthorizationController {

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
        String lectureCode = account.getLecture().getLectureCode();
        GroupDAO grDAO = new GroupDAO();
        SessionDAO sesDAO = new SessionDAO();
        HashMap<Group, Integer> groups = grDAO.getGroupsByLectureCode(lectureCode);
        request.setAttribute("groups", groups);
        String groupId =  request.getParameter("groupId");
        request.setAttribute("lectureCode", lectureCode);
        if(groupId != null){
            ArrayList<Session> sessions = sesDAO.getSesByGroupIdAndLectureCode(groupId, lectureCode);
            request.setAttribute("sessions", sessions);
        }
        request.getRequestDispatcher("../view/lecture/takeatt.jsp").forward(request, response);
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

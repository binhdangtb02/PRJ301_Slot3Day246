/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Dell
 */
public abstract class BaseAuthenticationController extends HttpServlet {

    public boolean isAuthenticated(HttpServletRequest request) {
        return request.getSession().getAttribute("account") != null;
    }

    protected abstract void processGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException;

    protected abstract void processPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthenticated(request)) {
            processGet(request, response);
        } else {
            response.getWriter().println("access denied");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAuthenticated(request)) {
            processPost(request, response);
        } else {
            response.getWriter().println("access denied");
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Feature;
import model.Role;

/**
 *
 * @author Dell
 */
public abstract class BaseAuthorizationController extends BaseAuthenticationController {

    public boolean checkAutorization(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getServletPath();
        Account account = (Account) request.getSession().getAttribute("account");
        for (Role role : account.getRoles()) {
            for (Feature feature : role.getFeatures()) {
                if (feature.getFurl().equals(url)) {
                    return true;
                }
            }
        }
        return false;
    }



    protected abstract void processAuthorizationGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected abstract void processAuthorizationPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (checkAutorization(request, response)) {

                processAuthorizationPost(request, response);

//
                response.getWriter().println("access denied");

        } 
        else {
            response.getWriter().println("access denied");
        }
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (checkAutorization(request, response)) {

                processAuthorizationGet(request, response);

        } else {

            response.getWriter().println("access denied");

        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Feature;
import model.Role;

/**
 *
 * @author Dell
 */
public abstract class BaseAuthorizationController extends BaseAuthenticationController{
    public boolean checkAutorization(HttpServletRequest request, HttpServletResponse response){
        String url = request.getServletPath();
        Account account = (Account)request.getSession().getAttribute("account");
        for (Role role : account.getRoles()) {
            for (Feature feature : role.getFeatures()) {
                if(feature.getFurl().equals(url)){
                    return true;
                }
            }
        }
        return false;
    }
    
    protected abstract  void processAuthorizationGet(HttpServletRequest request, HttpServletResponse response);
    protected abstract  void processAuthorizationPoss(HttpServletRequest request, HttpServletResponse response);
    @Override
    protected  void processPost(HttpServletRequest request, HttpServletResponse response){
        if(checkAutorization(request, response)){
            processAuthorizationGet(request, response);
        }
        else{
            
        }
    }
    @Override
    protected  void processGet(HttpServletRequest request, HttpServletResponse response){
         if(checkAutorization(request, response)){
            processAuthorizationPoss(request, response);
        }
        else{
            
        }
    }
}

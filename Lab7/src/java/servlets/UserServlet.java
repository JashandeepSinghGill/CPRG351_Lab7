/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.RoleService;
import services.UserService;

/**
 *
 * @author Jashan Gill
 */
public class UserServlet extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
         UserService ns = new UserService();
         RoleService rs = new RoleService();    

        try {
            HttpSession session = request.getSession();
             
            List<User> users = ns.getAll();
            
            session.setAttribute("users", users);
        } catch (Exception ex) {
            request.setAttribute("message", "error");
        }
        
        String action = request.getParameter("act");
        if(!(action == null || action.equals("")) && action.equals("edit")){
            try {
                HttpSession session = request.getSession();
                
                String email = request.getParameter("email");
                session.setAttribute("email",email);
                User user = ns.get(email);
                
                request.setAttribute("user", user);
            } catch (Exception ex) {
                request.setAttribute("message", "error");
            }
        }
        
        if(!(action == null || action.equals("")) && action.equals("del")){
            try {
                
                String email = request.getParameter("email");
                ns.delete(email);
                request.setAttribute("message", "delete");
                
                HttpSession session = request.getSession();
                List<User> users = ns.getAll();
                session.setAttribute("users", users);
                
            } catch (Exception ex) {
                request.setAttribute("message", "error");
            }
        }
                
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
                UserService us = new UserService();
                
               String action = request.getParameter("action");
               
               if(action.equalsIgnoreCase("add")){
                   
                String email = request.getParameter("email");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String password = request.getParameter("password");
                int role = Integer.parseInt(request.getParameter("role"));
                
                    try {
                        us.insert(email, firstName, lastName, password, role);
                        request.setAttribute("message", "create");
                        
                         HttpSession session = request.getSession();
                         List<User> users = us.getAll();
                         session.setAttribute("users", users);
                    } catch (Exception ex) {
                        Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                        request.setAttribute("message", "error"); 
                    } 
               }
        
               else{
                   
                   HttpSession session = request.getSession();             
                   String email = (String)session.getAttribute("email");
                   String firstName = request.getParameter("firstName");
                   String lastName = request.getParameter("lastName");
                   int role = Integer.parseInt(request.getParameter("role"));
                   String isActive = request.getParameter("active") == null?"false":"true";
                   boolean active = isActive.equalsIgnoreCase("true");
                   
                    try {
                        us.update(email, firstName, lastName, role, active);
                         List<User> users = us.getAll();
                         session.setAttribute("users", users);
                         session.setAttribute("email","");
                    } catch (Exception ex) {
                        Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                        request.setAttribute("message", "error");
                    }
               }
               
               getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }


}

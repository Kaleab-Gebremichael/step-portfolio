
package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signin")
public class SignIn extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      response.sendRedirect("/discussion.html");
    
    } else {
      String loginUrl = userService.createLoginURL("/discussion.html");

      response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a> before accessing discussions.</p>");      
    }

  }
}

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.google.gson.Gson;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/discussion")
public class DiscussionServlet extends HttpServlet {

  private ArrayList<String> classicRock;
  
  @Override
  public void init(){
    
    classicRock = new ArrayList<>();
    
    classicRock.add("Sultans of Swing");
    classicRock.add("Thunderstruck");
    classicRock.add("Burnin' for you");
    classicRock.add("Holy Diver");

  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;");

    String json = convertToJson(classicRock);

    response.getWriter().println(json);
  }

  private String convertToJson(ArrayList<String> data) {
    Gson gson = new Gson();
    String json = gson.toJson(data);
    return json;
  }

}



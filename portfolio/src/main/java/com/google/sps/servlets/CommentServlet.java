package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.sps.data.Post;
import java.lang.Math;


@WebServlet("/reply")
public class CommentServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String postIdWithComment = getParameter(request, "postId", "");
    String replyContent = getParameter(request, "reply-content", "");

    Entity replies = new Entity("Replies");
    replies.setProperty("postId", postIdWithComment);
    replies.setProperty("replyContent", replyContent);
    replies.setProperty("replyTime", System.currentTimeMillis());

    DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
    dataStore.put(replies);

    response.sendRedirect("/discussion.html");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);

    if (value == null) {
      return defaultValue;
    }
    
    return value;
  }


  private String convertToJson(ArrayList<Post> data) {
    Gson gson = new Gson();
    String json = gson.toJson(data);
    return json;
  }
}

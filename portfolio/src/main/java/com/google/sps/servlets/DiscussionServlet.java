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


@WebServlet("/discussion")
public class DiscussionServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    ArrayList<Post> allPosts = new ArrayList<Post>();
      
    Query query = new Query("Post").addSort("commentTime", SortDirection.ASCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery result = datastore.prepare(query);

    for (Entity entity : result.asIterable()) {
      String postTitle = (String) entity.getProperty("postTitle");
      String postContent = (String) entity.getProperty("postContent");
      long postId = (long) entity.getProperty("postId");

      Post curPost = new Post(postTitle, postContent, postId);
      allPosts.add(curPost);

    }

    String json = convertToJson(allPosts);

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String postTitle = getParameter(request, "post-title", "");
    String postContent = getParameter(request, "post-content", "");

    Post newPost = new Post(postTitle, postContent);

    Entity post = new Entity("Post");
    post.setProperty("postTitle", newPost.getTitle());
    post.setProperty("postContent", newPost.getContent());
    post.setProperty("postId", newPost.getId());
    post.setProperty("commentTime", System.currentTimeMillis());

    DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
    dataStore.put(post);

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

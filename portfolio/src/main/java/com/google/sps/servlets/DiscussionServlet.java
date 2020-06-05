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
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.sps.data.Post;
import com.google.sps.data.Reply;
import java.lang.Math;


@WebServlet("/discussion")
public class DiscussionServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    ArrayList<Post> allPosts = new ArrayList<Post>();
      
    Query postQuery = new Query("Post").addSort("commentTime", SortDirection.ASCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery posts = datastore.prepare(postQuery);

    for (Entity entity : posts.asIterable()) {
      
      String postTitle = (String) entity.getProperty("postTitle");
      String postContent = (String) entity.getProperty("postContent");
      String postId = (String) entity.getProperty("postId");

      Post curPost = new Post(postTitle, postContent, postId);

      Filter keyFilter =  new FilterPredicate("postId", FilterOperator.EQUAL, postId);
      Query replyQuery = new Query("Replies").setFilter(keyFilter);
      replyQuery.addSort("replyTime", SortDirection.ASCENDING);

      PreparedQuery replies = datastore.prepare(replyQuery);

      for (Entity cur : replies.asIterable()) {
        String replyContent = (String) cur.getProperty("replyContent");
        String replyTime = String.valueOf(cur.getProperty("replyTime"));

        Reply curReply = new Reply(replyContent, replyTime);

        curPost.addReply(curReply);
      }

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

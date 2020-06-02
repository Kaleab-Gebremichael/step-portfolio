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


@WebServlet("/discussion")
public class DiscussionServlet extends HttpServlet {
  private ArrayList<String> classicRock;

  @Override
  public void init() {
    classicRock = new ArrayList<>();
    classicRock.add("Sultans of Swing");
    classicRock.add("Thunderstruck");
    classicRock.add("Burnin' for you");
    classicRock.add("Holy Diver");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;");

    Query query = new Query("Classic-Rock");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery result = datastore.prepare(query);

    for (Entity entity : result.asIterable()) {
      String songName = (String) entity.getProperty("songName");
      classicRock.add(songName);
    }

    String json = convertToJson(classicRock);
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String songName = getParameter(request, "song-name", "");

    Entity classicRock = new Entity("Classic-Rock");
    classicRock.setProperty("songName", songName);

    DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
    dataStore.put(classicRock);

    response.sendRedirect("/discussion.html");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;

  }

  private String convertToJson(ArrayList<String> data) {
    Gson gson = new Gson();
    String json = gson.toJson(data);
    return json;
  }
}

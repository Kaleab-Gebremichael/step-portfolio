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


@WebServlet("/discussion")
public class DiscussionServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    ArrayList<String> classicRock = new ArrayList<>();
    classicRock.add("Sultans of Swing");
    classicRock.add("Thunderstruck");
    classicRock.add("Burnin' for you");
    classicRock.add("Holy Diver");

    Query query = new Query("Classic-Rock").addSort("commentTime", SortDirection.ASCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery result = datastore.prepare(query);

    for (Entity entity : result.asIterable()) {
      String songName = (String) entity.getProperty("songName");
      classicRock.add(songName);
    }

    String json = convertToJson(classicRock);

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String songName = getParameter(request, "song-name", "");

    Entity song = new Entity("Classic-Rock");
    song.setProperty("songName", songName);
    song.setProperty("commentTime", System.currentTimeMillis());

    DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
    dataStore.put(song);

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

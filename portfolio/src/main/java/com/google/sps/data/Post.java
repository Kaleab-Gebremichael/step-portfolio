
package com.google.sps.data;

import java.util.ArrayList;

/** Class representing an individual post. */
public class Post {
  private String title;
  private String content;
  private String id;
  private ArrayList<String> replies;

  public Post(String title, String content) {
    this.title = title;
    this.content = content;
    this.id = uniqueIdentifierGenerator();
    this.replies = new ArrayList<>();
  }

  public Post(String title, String content, String id){
    this.title = title;
    this.content = content;
    this.id = id;
    this.replies = new ArrayList<>();
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public String getId(){
    return id;
  }

  public ArrayList<String> getReplies(){
    return replies;
  }

  public void addReply(String reply){
    replies.add(reply);
  }

  private String uniqueIdentifierGenerator(){
    long uniqueID = (long) (Math.random()*1001) + 1;
    return String.valueOf(uniqueID);
  }
}
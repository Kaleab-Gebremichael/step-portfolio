
package com.google.sps.data;

import com.google.sps.data.Reply;
import java.util.ArrayList;

/** Class representing an individual post. */
public class Post {
  private String title;
  private String content;
  private String id;
  private ArrayList<Reply> replies;

  public Post(String title, String content) {
    this.title = title;
    this.content = content;
    this.id = uniqueIdentifierGenerator();
    this.replies = new ArrayList<Reply>();
  }

  public Post(String title, String content, String id){
    this.title = title;
    this.content = content;
    this.id = id;
    this.replies = new ArrayList<Reply>();
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

  public ArrayList<Reply> getReplies(){
    return replies;
  }

  public void addReply(Reply singleReply){
    replies.add(singleReply);
  }

  private String uniqueIdentifierGenerator(){
    long uniqueID = (long) (Math.random()*1001) + 1;
    return String.valueOf(uniqueID);
  }
}

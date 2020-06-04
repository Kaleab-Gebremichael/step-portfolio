
package com.google.sps.data;

/** Class representing an individual post. */
public class Post {
  private String title;
  private String content;
  private long id;

  public Post(String title, String content) {
    this.title = title;
    this.content = content;
    this.id = uniqueIdentifierGenerator();
  }

  public Post(String title, String content, long id){
    this.title = title;
    this.content = content;
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public long getId(){
    return id;
  }

  private long uniqueIdentifierGenerator(){
    long uniqueID = (long) (Math.random()*1001) + 1;
    return uniqueID;
  }
}

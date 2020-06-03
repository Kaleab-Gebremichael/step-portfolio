
package com.google.sps.data;


/**
 * Class representing an individual post.
 *
 */
public class Post {

  private String title;

  private String content;


  /** Returns whether this game has ended. */
  public Post(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public String getTitle(){
    return title;
  }

  public String getContent(){
    return content;
  }
}

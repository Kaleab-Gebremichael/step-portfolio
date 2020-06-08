
package com.google.sps.data;

/** Class representing an individual reply. */
public class Reply {
  private String userEmail;
  private String content;
  private String replyTime;

  public Reply(String content, String replyTime, String userEmail) {
    this.content = content;
    this.replyTime = replyTime;
    this.userEmail = userEmail;
  }

  public String getContent() {
    return content;
  }

  public String getReplyTime() {
    return replyTime;
  }

  public String getUserEmail() {
    return userEmail;
  }
}

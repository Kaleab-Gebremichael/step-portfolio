
package com.google.sps.data;

/** Class representing an individual reply. */
public class Reply {
  private String content;
  private String replyTime;

  public Reply(String content, String replyTime) {
    this.content = content;
    this.replyTime = replyTime;
  }

  public String getContent() {
    return content;
  }

  public String getReplyTime() {
    return replyTime;
  }
}

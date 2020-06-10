
function checkTranslation(){

  let input = document.getElementById("language");
  let languageCode = input.options[input.selectedIndex].value;
  displayList("language=" + languageCode);
}


function displayList(parameter) {
  fetch('/discussion?' + parameter).then((response) => response.json()).then((data) => {

    //clear out page before loading
    document.getElementById('discussion-container').textContent = '';

    let posts = document.createElement('div');

    for (let i = 0; i < data.length; ++i) {

      let replyTime = new Date(parseInt(data[i].postTime)).toLocaleString();
      let userEmail = data[i].userEmail;

      let userIdentification = document.createElement("div");
      userIdentification.textContent = `>>${userEmail} wrote on ${replyTime}`;

      let title = document.createElement('div');
      title.classList.add("post-title");
      title.textContent = data[i].title;

      let content = document.createElement('div');
      content.classList.add("post-content");
      content.textContent = data[i].content;

      let replies = prepareReplies(data[i].replies);
      replies.classList.add("post-replies");

      let singlePost = document.createElement('div');
      singlePost.classList.add("post");

      let replyButton = document.createElement('button');
      replyButton.textContent = "Reply to this post";

      singlePost.appendChild(userIdentification);
      singlePost.appendChild(title);
      singlePost.appendChild(content);
      singlePost.appendChild(replies);
      singlePost.appendChild(replyButton);

      posts.appendChild(singlePost);

      replyButton.addEventListener("click", () => {
        replyButton.classList.add("hidden");
        createReplyForm(singlePost, data[i].id);
      });
    }

    document.getElementById('discussion-container').appendChild(posts);
  })
}

/**
 *  This function creates a form that lets user reply to the comment of their choosing
 *  and then displays it underneath the post.
 *  @param {!Element} singlePost Post object that is being replied to
 *  @param {number} postId Id of the post being replied to
 */
function createReplyForm(singlePost, postId){

  let replyForm = document.createElement("form");
  replyForm.action = `/reply?postId=${postId}`;
  replyForm.method = "POST";

  let inputBox = document.createElement("textarea");
  inputBox.name = "reply-content"
  inputBox.rows = "2";
  inputBox.cols = "40";

  let submitButton = document.createElement("input");
  submitButton.type = "submit";
  submitButton.value = "Post";

  replyForm.appendChild(inputBox);
  replyForm.appendChild(submitButton);

  singlePost.appendChild(replyForm);
}

/**
 *  This function organizes the replies of a given post
 *  @param {!Array<!Object>} singlePostReplies Array of replies to a single post
 *  @returns {!Element} Organized version of replies
 */
function prepareReplies(singlePostReplies){

  let allReplyContainer = document.createElement("div");

  for (let i = 0; i < singlePostReplies.length; ++i) {

    let replyTime = new Date(parseInt(singlePostReplies[i].replyTime)).toLocaleString();
    let userEmail = singlePostReplies[i].userEmail;

    let singleReply = document.createElement("div");

    let userIdentification = document.createElement("div");
    userIdentification.textContent = `>>>>${userEmail} wrote on ${replyTime}:`;

    let replyContent = document.createElement("div");
    replyContent.textContent = singlePostReplies[i].content;
    replyContent.classList.add("reply-content");
    
    singleReply.appendChild(userIdentification);
    singleReply.appendChild(replyContent);

    allReplyContainer.appendChild(singleReply);
  }

  return allReplyContainer;
}

function displayList() {
  fetch('/discussion').then((response) => response.json()).then((data) => {

    let posts = document.createElement('div');

    for (let i = 0; i < data.length; ++i) {
      let title = document.createElement('div');
      title.classList.add("post-title");
      title.textContent = data[i].title;

      let content = document.createElement('div');
      content.classList.add("post-content");
      content.textContent = data[i].content;

      let singlePost = document.createElement('div');
      singlePost.classList.add("post");

      let replyButton = document.createElement('button');
      replyButton.textContent = "Reply to this post";

      singlePost.appendChild(title);
      singlePost.appendChild(content);
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
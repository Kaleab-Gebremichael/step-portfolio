
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
      singlePost.id = uniqueIdentifierGenerator();  //as a unique identifier to the post.

      let replyButton = document.createElement('button');
      replyButton.textContent = "Reply to this post";
      replyButton.onclick = `createReplyForm(${singlePost.id})`;


      singlePost.appendChild(title);
      singlePost.appendChild(content);
      singlePost.appendChild(replyButton);

      posts.appendChild(singlePost);
    }

    document.getElementById('discussion-container').appendChild(posts);
  })
}

function createReplyForm(postId){

  let replyForm = document.createElement("form");
  replyForm.action = `/reply?postID=${postId}`;
  replyForm.method = "POST";

  let inputBox = document.createElement("textarea");
  inputBox.rows = "2";
  inputBox.cols = "40";

  let submitButton = document.createElement("input");
  submitButton.type = "submit";
  submitButton.value = "Post";

  replyForm.appendChild(inputBox);
  replyForm.appendChild(submitButton);

  document.getElementById(postId).appendChild(replyForm);
}

function uniqueIdentifierGenerator(){
   const identifier = Math.floor(Math.random() * 1000) + 1;

   return identifier;
}

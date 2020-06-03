
function displayList() {
  fetch('/discussion').then((response) => response.json()).then((data) => {


    let posts = document.createElement('div');

    for (let i = 0; i < data.length; ++i) {
      let title = document.createElement('div');
      title.id = "post-title";
      title.textContent = data[i].title;

      let content = document.createElement('div');
      content.id = "post-content";
      content.textContent = data[i].content;


      let singlePost = document.createElement('div');
      singlePost.id = "post";

      singlePost.appendChild(title);
      singlePost.appendChild(content);

      posts.appendChild(singlePost);
    }

    document.getElementById('discussion-container').appendChild(posts);
  })
}

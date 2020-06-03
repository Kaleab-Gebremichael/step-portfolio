
function displayList() {
  fetch('/discussion').then((response) => response.json()).then((data) => {


    let unorderedList = document.createElement('ul');

    for (let i = 0; i < data.length; ++i) {
      let listItem = document.createElement('li');
      listItem.textContent = data[i];
      unorderedList.appendChild(listItem);
    }

    document.getElementById('discussion-container').appendChild(unorderedList);
  })
}

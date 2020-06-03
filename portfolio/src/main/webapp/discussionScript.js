
function displayList() {
  fetch('/discussion').then((response) => response.json()).then((data) => {
    document.getElementById('discussion-container').textContent =
        data.join(', ');
  })
}

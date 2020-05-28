// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

const SPOTIFY_LINK = "https://open.spotify.com/playlist/6rmiBWfdA4jGdTWaYMAQri";



/* Sends user a spotify link.*/
function sendSpotifyLink(){
    const linkContainer = document.getElementById("spotify-link-container");
    linkContainer.innerHTML = "<em>Jazz isn't an avenue I've explored!</em> Here is the spotify link: \n" + SPOTIFY_LINK;
}

// document.getElementById("quiz").addEventListener("submit", function(event) {
//   const data = new FormData(form);
//   let output = "";
//   for (const entry of data) {
//     output = output + entry[0] + "=" + entry[1] + "\r";
//   }

//   console.log(output);
// });

let clickedItems = [];
let currentQuestionNumber = 0;

document.getElementById("quiz").addEventListener("click", function(event){
    clickedItems.push(event.srcElement.id);
    generateNextQuestion(currentQuestionNumber++);

});

let question2 = {
    question: "Which soccer team do I support?",
    choice1: "Manchester City",
    choice2: "Liverpool"
}

let question3 = {
    question: "Which of the following apps don't I have on my phone?",
    choice1: "Tiktok",
    choice2: "Reddit"
}

const questionList = [question2,question3];

function generateNextQuestion(questionNumber){
    if(questionNumber != questionList.length){
        document.getElementsByClassName("question")[0].innerText = questionList[questionNumber].question;
        document.getElementsByClassName("choice-1")[0].innerText = questionList[questionNumber].choice1;
        document.getElementsByClassName("choice-2")[0].innerText = questionList[questionNumber].choice2;
    
    } else {
        calculateScore();
    }

}

/**
 * Adds a random greeting to the page.
 */
function addRandomMovie() {
  const movies =
      ['Austin Powers', 'Ace Ventura', 'Talladega Nights', 'The dictator'];

  // Pick a random greeting.
  const movie = movies[Math.floor(Math.random() * movies.length)];

  // Add it to the page.
  const movieContainer = document.getElementById('movie-container');
  movieContainer.innerText = movie;
}
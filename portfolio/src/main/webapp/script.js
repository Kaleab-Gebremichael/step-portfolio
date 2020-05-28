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
    const scoreContainer = document.getElementById("score-container");
    scoreContainer.innerHTML = "<em>Jazz isn't an avenue I've explored!</em> Here is the spotify link: \n" + SPOTIFY_LINK;
}


let clickedItems = [];
let rightAnswers = ["choice-2", "choice-2", "choice-1"];
let currentQuestionNumber = 0;

document.getElementById("quiz").addEventListener("click", function(event){
    if(currentQuestionNumber != questionList.length){
        clickedItems.push(event.srcElement.id);
        generateNextQuestion(currentQuestionNumber++);
    } else {
        document.getElementById("quiz").classList.add("hidden");

        const scoreContainer = document.getElementById("score-container");
        scoreContainer.innerHTML = "Please click Finish to calculate your score";
    }

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
    document.getElementsByClassName("question")[0].innerText = questionList[questionNumber].question;
    document.getElementsByClassName("choice-1")[0].innerText = questionList[questionNumber].choice1;
    document.getElementsByClassName("choice-2")[0].innerText = questionList[questionNumber].choice2;
}

document.getElementById("button").addEventListener("click",function(){
    document.getElementById("button").classList.add("hidden");
    let score = 0;
    for(let i = 0; i < rightAnswers.length; ++i){
        if(clickedItems[i] == rightAnswers[i]){
            ++score;
        }
    }

    const scoreContainer = document.getElementById("score-container");
    if(score > questionList.length/2){
        scoreContainer.innerHTML = "Your score is " + score + 
            "! Here is the spotify link for you to enjoy: \n" + SPOTIFY_LINK;
    } else {
        scoreContainer.innerHTML = "Your score is " + score;
    }
    

});

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
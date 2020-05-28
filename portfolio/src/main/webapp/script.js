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

const RIGHT_ANSWERS = ["choice-2", "choice-2", "choice-1"];

const MOVIES =
      ['Austin Powers', 'Ace Ventura', 'Talladega Nights', 'The dictator'];

const QUESTION_2 = {
    question: "Which soccer team do I support?",
    choice1: "Manchester City",
    choice2: "Liverpool"
}

const QUESTION_3 = {
    question: "Which of the following apps don't I have on my phone?",
    choice1: "Tiktok",
    choice2: "Reddit"
}

const QUESTIONS_LIST = [QUESTION_2,QUESTION_3];

let clickedItems = [];
let currentQuestionNumber = 0;

/** 
 *  This function records the choices of the user and makes call to next 
 *  question generator if appropriate. 
*/
document.getElementById("quiz").addEventListener("click", function(event){
    document.getElementById("quiz").reset();

    if(currentQuestionNumber != QUESTIONS_LIST.length){
        clickedItems.push(event.srcElement.id);
        generateNextQuestion(currentQuestionNumber++);
    
    } else {
        document.getElementById("quiz").classList.add("hidden");

        const scoreContainer = document.getElementById("score-container");
        scoreContainer.innerHTML = "Please click Finish to calculate your score";
    }
});


/**
    This function displays the next question available.
    @type {number} questionNumber - the question to display
 */
function generateNextQuestion(questionNumber){
    document.getElementsByClassName("question")[0].innerText = QUESTIONS_LIST[questionNumber].question;
    document.getElementsByClassName("choice-1")[0].innerText = QUESTIONS_LIST[questionNumber].choice1;
    document.getElementsByClassName("choice-2")[0].innerText = QUESTIONS_LIST[questionNumber].choice2;
}


/** This function calculates and displays the results from the quiz. */
document.getElementById("button").addEventListener("click",function(){
    document.getElementById("button").classList.add("hidden");
    
    let score = 0;
    for(let i = 0; i < RIGHT_ANSWERS.length; ++i){
        if(clickedItems[i] == RIGHT_ANSWERS[i]){
            ++score;
        }
    }

    const scoreContainer = document.getElementById("score-container");
    
    if(score > QUESTIONS_LIST.length/2){
        scoreContainer.innerHTML = "Your score is " + score + 
            "! Here is the spotify link for you to enjoy: \n" + SPOTIFY_LINK;
    } else {
        scoreContainer.innerHTML = "Your score is " + score;
    }
});


/** Adds a random greeting to the page. */
function addRandomMovie() {
  // Pick a random greeting.
  const movie = movies[Math.floor(Math.random() * movies.length)];

  // Add it to the page.
  const movieContainer = document.getElementById('movie-container');
  movieContainer.innerText = movie;
}
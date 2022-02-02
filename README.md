# Getting Started

## Run the demo locally
To build and start the application run the following comments.

1. Run a Gradle build (`./gradlew build`)
2. Start the application with:
    * Gradle: `./gradlew bootRun`
    * IntelliJ: use a Spring run configuration
        - Main Class: `net.trivia.demo.DemoApplication`

You can test the demo by sending requests to the endpoints, for instance using Postman.
    * GET: `localhost:8080/questions`
    * POST: `localhost:8080/checkAnswers`, with JSON body like:
    ```{
           "questionsAndChosenAnswers": [
               {
                   "question": "",
                   "chosenAnswer": ""
               },
               {
                   "question": "",
                   "chosenAnswer": ""
               },
               {
                   "question": "",
                   "chosenAnswer": ""
               },
               {
                   "question": "",
                   "chosenAnswer": ""
               },
               {
                   "question": "",
                   "chosenAnswer": ""
               }
           ]
       }```

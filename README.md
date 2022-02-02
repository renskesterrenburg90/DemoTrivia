# Getting Started

## Run the demo locally
To build the application run the following comment:

1. Run a Gradle build (`./gradlew build`)
2. Start the application with:
    * Gradle: `./gradlew bootRun`
    * IntelliJ: use a Spring run configuration
        - Main Class: `net.trivia.demo.DemoApplication`

This will start a local server on port 8080. To start the demo, you can perform the following rest call:

http://localhost:8080/questions
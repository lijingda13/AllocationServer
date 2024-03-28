# Web Server for CS 5031-P3

## Overview

This is a web server application for the coursework project CS5031-P3. The server is implemented in Java using the Spring framework. It provides RESTful HTTP APIs for both the frontend application (a GUI application) and the Terminal application. This application uses an H2 database for data storage and initializes the database with sample data. The schema.sql file creates the database tables, while the data.sql file inserts the sample data.

## Development

Ensure you have Maven version 3.8.1 or higher and Java JDK 21 installed for system development.
### Run the Back-End (Spring Application)

You can run the back-end application using the following commands:
```shell
# move to the allocated-server directory
cd allocated-server
# Check if Java is installed, Install if not
java -version
mvn -version
# Install the dependencies
mvn clean install
# Start the allocated-server application
mvn spring-boot:run
```

### Build and Deploy
```shell
# Build the Spring application
mvn clean package
# Copy the JAR file to your server(Replace /path/to/server with the actual path) or Upload the JAR file to the server 
cp target/allocated-server-0.0.1-SNAPSHOT.jar /path/to/server
# Start the application
java -jar /path/to/server/allocated-server-0.0.1-SNAPSHOT.jar
```

## Testing
You can run unit tests using Maven by executing `mvn test` in the project root. This will execute all JUnit5 tests located in the `src/test` directory.
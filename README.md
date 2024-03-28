# cs5031-p3

# Allocation Server

## Overview

This is a web server application for the coursework project CS5031-P3. The server is implemented in Java using the
Spring framework. It provides RESTful HTTP APIs for both the frontend application (a GUI application) and the Terminal
application. This application uses an H2 database for data storage and initializes the database with sample data. The
schema.sql file creates the database tables, while the data.sql file inserts the sample data.

## Development

Ensure you have Maven version 3.8.1 or higher and Java JDK 21 installed for system development.

### Run the Back-End (Spring Application)

You can run the back-end application using the following commands:

```shell
# move to the allocated-server directory
cd allocated-server
# Check if Java is installed, Install if not
java -version
# Check if Maven is installed, Install if not
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
cp target/allocation-0.0.1-SNAPSHOT.jar /path/to/server
# Start the application
java -jar /path/to/server/allocation-0.0.1-SNAPSHOT.jar

#Or simply use
mvn clean package
#Replace /path/to/server/target with the actual path
java -jar /path/to/server/target/allocation-0.0.1-SNAPSHOT.jar
```

## Testing

You can run unit tests using Maven by executing `mvn test` in the project root. This will execute all JUnit5 tests
located in the `src/test` directory.

# Web Frontend Client

## Installation

Install the application dependencies by running:

```sh
npm install
```

## Development

Start the application in development mode by running:

```sh
npm run dev
```

## Production

Build the application in production mode by running:

```sh
npm run build
```

## DataProvider

The included data provider
use [ra-data-json-server](https://github.com/marmelab/react-admin/tree/master/packages/ra-data-json-server).

## Project structure

```
├── README.md
├── index.html // the entry HTML file
├── package-lock.json //lockfile: ensuring consistent dependency versions across installations
├── package.json // the configuration file 
├── prettier.config.js // specify code formatting rules
├── public // public assets
│   ├── favicon.ico
│   └── manifest.json
├── src
│   ├── auth  //  files related to authentication
│   │   ├── MyLoginPage.tsx // Custom login page component
│   │   └── authProvider.ts // Authentication provider 
│   ├── index.tsx // entry component
│   ├── pages // page components and data processing related files
│   │   ├── App.tsx
│   │   ├── Dashboard.tsx
│   │   ├── dataProvider.ts
│   │   ├── layout.tsx
│   │   ├── projects.tsx
│   │   ├── users.tsx
│   │   └── vite-env.d.ts
│   └── share
│       ├── env.ts // Environment variable 
│       ├── styles.css // Shared style file
│       └── url.ts // Shared URL file
├── tsconfig.json
└── vite.config.ts
```

# Terminal Client

## Build and Deploy

The terminal client lives in the /client directory. Run the following command in the terminal
or command prompt from the root directory:

```shell
# move to the terminal client directory
cd client

# Build the application
mvn clean package

#run the application
java -jar target/client-1.0-SNAPSHOT-jar-with-dependencies.jar
```

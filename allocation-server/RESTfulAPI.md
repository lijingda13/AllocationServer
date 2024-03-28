# RESTful API Documentation

## Introduction

This document describes the RESTful API for the **Allocation** Application. The API is designed based on the REST
principles to provide HTTP interfaces for the client to interact with the server.

### Login

This API is used to authenticate the user and provide a JWT token for the user to access the protected resources.

- **Endpoint**: `POST /api/auth/login`
- **Request Body**:
    - `username`: The username of the user
    - `password`: The password of the user
- **Response**:
    - Status Code:
        - `200`: The user is successfully authenticated
        - `401`: The user is not authenticated
    - Response Body:
        - `result`: The result of the response
        - `msg`: The message for the response
        - `token`: The JWT token for the user
        - `userId`: The user id of the user
        - `user`: The user information

- **Example**:
- request:

```shell
curl --location 'http://localhost:8080/api/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "username": "rwilliams",
    "password": "123456"
}'
```

- Response:

```json
{
  "result": true,
  "message": "Login successful",
  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlNUQUZGIiwiZXhwIjoxNzExMDE3MDc1LCJ1c2VybmFtZSI6InJ3aWxsaWFtcyJ9.Ou3Pnb7MMS_eYomz5Te7eJWnQ262eeTlbJIkwy4Q6hA",
  "userId": 1,
  "user": {
    "id": 1,
    "username": "rwilliams",
    "password": "123456",
    "email": "rwilliams@gmail.com",
    "role": "STAFF",
    "firstName": "Rachel",
    "lastName": "Williams"
  }
}
```

### Register a new user

This API is used to register a new user in the Allocation Application.

- **Endpoint:** `POST /api/users` (please note not the `/api/users/`)
- **Request Body:**
    - `username`: The desired username of the new user. Must be unique. (Required)
    - `password`: The password for the new user. (Required)
    - `role`: The role of the new user (STAFF or STUDENT). (Required)
    - `firstName`: The first name of the new user. (Required)
    - `lastName`: The last name of the new user. (Required)
    - `email`: The email address of the new user. (Optional)
- **Response:**
    - Status Code:
        - `200`: Registration is successful.
        - `400`: Registration failed due to a non-unique username or other bad requests.
    - Response Body: A plain text message indicating the outcome of the operation.
        - Success Response: "Registration successful"
        - Failure Response: "Failed: Username has existed" (in case the username is not unique)

Example:

- Request:

```shell
curl --location 'http://localhost:8080/api/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "test",
    "password": "123456", 
    "role": "STAFF", 
    "firstName": "TestF",
    "lastName": "TestL",
    "email": "test@gmail.com"
}'
```

- Response(Success):

```text
"Registration successful"
```

- Response(Failure):

```text
"Failed: Username has existed"
```

### Get user information

- **Endpoint:** `GET /api/users/{userId}`
- **Request Header:**
    - Authorization: "Bearer {JWT token}"
- **Response:**
    - Status Code:
        - `200`: The user information is successfully retrieved.
        - `403`: The user is not authorized to access the resource.
        - `404`: The user is not found.
    - Response Body: The user information in JSON format.
        - `id`: The user id.
        - `username`: The username of the user.
        - `role`: The role of the user.
        - `firstName`: The first name of the user.
        - `lastName`: The last name of the user.
        - `email`: The email address of the user.
- **Example:**
- Request:

```shell
curl --location 'http://localhost:8080/api/users/1' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlNUQUZGIiwiZXhwIjoxNzExMDE3NDM4LCJ1c2VybmFtZSI6InJ3aWxsaWFtcyJ9.pmAnq5LDcTsy9wvAMKMB35zoozkYfzxeBAq16DMqC6w'
```

- Response:

```json
{
  "id": 1,
  "username": "rwilliams",
  "password": "123456",
  "email": "rwilliams@gmail.com",
  "role": "STAFF",
  "firstName": "Rachel",
  "lastName": "Williams"
}
```

### Update user information(All information)

- **Endpoint:** `PUT /api/users/{userId}`
- **Request Header:**
    - Authorization : "Bearer { JWT token }"
- **Request Body:**
    - `id`: The user id. (Required)
    - `username`: The username of the user. (Optional)
    - `password`: The password of the user. (Optional)
    - `role`: The role of the user. (Optional)
    - `firstName`: The first name of the user. (Optional)
    - `lastName`: The last name of the user. (Optional)
    - `email`: The email address of the user. (Optional)
- **Response:**
    - Status Code:
        - `200`: The user information is successfully updated.
        - `403`: The user is not authorized to access the resource.
        - `404`: The user is not found.
        - `400`: The request is bad.
    - Response Body: the user information in JSON format.
        - `id`: The user id.
        - `username`: The username of the user.
        - `role`: The role of the user.
        - `firstName`: The first name of the user.
        - `lastName`: The last name of the user.
        - `email`: The email address of the user.
- **Example:**
- Request:

```shell
curl --location --request PUT 'http://localhost:8080/api/users/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlNUQUZGIiwiZXhwIjoxNzExMDE3NDM4LCJ1c2VybmFtZSI6InJ3aWxsaWFtcyJ9.pmAnq5LDcTsy9wvAMKMB35zoozkYfzxeBAq16DMqC6w' \
--data-raw '{
    "id": 1,
    "username": "rwilliams",
    "password": "1234567",
    "email": "rwilliams@gmail.com",
    "role": "STAFF",
    "firstName": "Rachel",
    "lastName": "Williams"
}'
```

- Response:

```json
{
  "id": 1,
  "username": "rwilliams",
  "password": "1234567",
  "email": "rwilliams@gmail.com",
  "role": "STAFF",
  "firstName": "Rachel",
  "lastName": "Williams"
}
```

### Update user information(Partial information)

- **Endpoint:** `PATCH /api/users/{userId}`
- **Request Header:**
    - Authorization : "Bearer { JWT token }"
- **Request Body:**
    - `id`: The user id. (Required)
    - `username`: The username of the user. (Optional)
    - `password`: The password of the user. (Optional)
    - `role`: The role of the user. (Optional)
    - `firstName`: The first name of the user. (Optional)
    - `lastName`: The last name of the user. (Optional)
    - `email`: The email address of the user. (Optional)
- **Response:**
    - Status Code:
        - `200`: The user information is successfully updated.
        - `403`: The user is not authorized to access the resource.
        - `404`: The user is not found.
        - `400`: The request is bad.
    - Response Body: the user information in JSON format.
- **Example:**
- Request:

```shell
curl --location --request PATCH 'http://localhost:8080/api/users/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlNUQUZGIiwiZXhwIjoxNzExMDE3NDM4LCJ1c2VybmFtZSI6InJ3aWxsaWFtcyJ9.pmAnq5LDcTsy9wvAMKMB35zoozkYfzxeBAq16DMqC6w' \
--data '{
    "password": "12345679"
}'
```

- Response:

```json
{
  "id": 1,
  "username": "rwilliams",
  "password": "12345679",
  "email": "rwilliams@gmail.com",
  "role": "STAFF",
  "firstName": "Rachel",
  "lastName": "Williams"
}
```

### get student information about the project

- **Endpoint:** `GET /api/users/{userId}/student-info`
- **Request Header:**
    - Authorization : "Bearer { JWT token }"
- **Response:**
    - Status Code:
        - `200`: The user information is successfully retrieved.
        - `403`: The user is not authorized to access the resource.
        - `404`: The user is not found.
        - `400`: The request is bad.
        - `500`: Internal server error
    - Response Body: the user information in JSON format.
        - `id`: The user id.
        - `username`: The username of the user.
        - `role`: The role of the user.
        - `firstName`: The first name of the user.
        - `lastName`: The last name of the user.
        - `email`: The email address of the user.
        - `assignedStatus`: The status of the student assignment.
        - `assignedProject`: The project assigned to the student.
- **Example:**
- Request:

```shell
curl --location 'http://localhost:8080/api/users/3/student-info' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwicm9sZSI6IlNUVURFTlQiLCJleHAiOjE3MTEwMjE4MzQsInVzZXJuYW1lIjoiamNoZW4ifQ.xqkGYkQFpddf0kIOmLwpEMW9JlBjycS8YuFIoJrAdPM'
```

- Response:

```json
{
  "id": 3,
  "username": "jchen",
  "password": "123456",
  "email": "jchen@gmail.com",
  "role": "STUDENT",
  "assignedStatus": true,
  "assignedProject": {
    "id": 1,
    "title": "AI in Education",
    "description": "Exploring the use of AI to personalize learning experiences.",
    "staff": {
      "id": 1,
      "username": "rwilliams",
      "password": "123456",
      "email": "rwilliams@gmail.com",
      "role": "STAFF",
      "firstName": "Rachel",
      "lastName": "Williams"
    },
    "status": true,
    "createDate": "2024-01-01T10:00:00.000+00:00"
  },
  "interestProjects": [
    {
      "id": 1,
      "title": "AI in Education",
      "description": "Exploring the use of AI to personalize learning experiences.",
      "staff": {
        "id": 1,
        "username": "rwilliams",
        "password": "123456",
        "email": "rwilliams@gmail.com",
        "role": "STAFF",
        "firstName": "Rachel",
        "lastName": "Williams"
      },
      "status": true,
      "createDate": "2024-01-01T10:00:00.000+00:00"
    },
    {
      "id": 2,
      "title": "Sustainable Computing",
      "description": "Investigating energy-efficient computing techniques for a sustainable future.",
      "staff": {
        "id": 1,
        "username": "rwilliams",
        "password": "123456",
        "email": "rwilliams@gmail.com",
        "role": "STAFF",
        "firstName": "Rachel",
        "lastName": "Williams"
      },
      "status": false,
      "createDate": "2024-01-02T11:00:00.000+00:00"
    }
  ],
  "firstName": "Jenny",
  "lastName": "Chen"
}
```

### Get all projects

This API is used to retrieve a list of all projects in the Allocation Application.

- **Endpoint:** `GET /api/projects`
- **Request Header:**
    - Authorization: "Bearer {JWT token}"
- **Response:**
    - Status Code:
        - `200`: Successfully retrieved the list of projects.
    - Response Body: A list of projects with their details.
        - `id`: Project id.
        - `title`: Title of the project.
        - `description`: Description of the project.
        - `staff`: Staff who proposed the project.
        - `status`: Status of the project.
        - `createDate`: Propose date of the project.

#### **Example:**

- Request:

```shell
curl --location 'http://localhost:8080/api/projects' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlNUQUZGIiwiZXhwIjoxNzExNjcwNjg5LCJ1c2VybmFtZSI6InJ3aWxsaWFtcyJ9.yEm1B94J77gMaV7vNCtx6WbAezoibrFqoUS7PmfpSGI'
```

- Response:

```json
[
  {
    "id": 1,
    "title": "AI in Education",
    "description": "Exploring the use of AI to personalize learning experiences.",
    "staff": {
      "id": 1,
      "username": "rwilliams",
      "password": "123456",
      "email": "rwilliams@gmail.com",
      "role": "STAFF",
      "firstName": "Rachel",
      "lastName": "Williams"
    },
    "status": true,
    "createDate": "2024-01-01 10:00:00"
  },
  {
    "id": 2,
    "title": "Sustainable Computing",
    "description": "Investigating energy-efficient computing techniques for a sustainable future.",
    "staff": {
      "id": 1,
      "username": "rwilliams",
      "password": "123456",
      "email": "rwilliams@gmail.com",
      "role": "STAFF",
      "firstName": "Rachel",
      "lastName": "Williams"
    },
    "status": false,
    "createDate": "2024-01-02 11:00:00"
  },
  {
    "id": 3,
    "title": "Data Privacy",
    "description": "Understanding the privacy implications of data collection and analysis.",
    "staff": {
      "id": 2,
      "username": "mjones",
      "password": "123456",
      "email": "mjones@gmail.com",
      "role": "STAFF",
      "firstName": "Michael",
      "lastName": "Jones"
    },
    "status": false,
    "createDate": "2024-01-03 12:00:00"
  },
  {
    "id": 4,
    "title": "Cybersecurity",
    "description": "Investigating the latest cybersecurity threats and defenses.",
    "staff": {
      "id": 2,
      "username": "mjones",
      "password": "123456",
      "email": "mjones@gmail.com",
      "role": "STAFF",
      "firstName": "Michael",
      "lastName": "Jones"
    },
    "status": false,
    "createDate": "2024-01-04 13:00:00"
  }
]
```

### Get all available projects for the student

This API provides the list of all available projects that a student can register interest in.

- **Endpoint:** `GET /api/students/{studentId}/available-projects`
- **Path Variable:**
    - `studentId`: The ID of the student.
- **Request Header:**
    - Authorization: "Bearer {JWT token}"
- **Response:**
    - Status Code:
        - `200`: Successfully retrieved the list of available projects.
        - `404`: No available projects found or student not found.
    - Response Body: A list of available projects for the student.
        - `id`: Project id.
        - `title`: Title of the project.
        - `description`: Description of the project.
        - `staff`: Staff who proposed the project.
        - `status`: Status of the project.
        - `createDate`: Propose date of the project.
        - `registerStatus`: Status of interest registration.

#### **Example:**

- Request:

```shell
curl --location 'http://localhost:8080/api/students/4/available-projects' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0Iiwicm9sZSI6IlNUVURFTlQiLCJleHAiOjE3MTE2NzE2MTcsInVzZXJuYW1lIjoianpoYW5nIn0.JLMPv1-f9UgotmFRsSr7QiaN1i3qO4G9OoFolpmsqQQ'
```

- Response:

```json
[
    {
        "id": 2,
        "title": "Sustainable Computing",
        "description": "Investigating energy-efficient computing techniques for a sustainable future.",
        "staff": {
            "id": 1,
            "username": "rwilliams",
            "password": "123456",
            "email": "rwilliams@gmail.com",
            "role": "STAFF",
            "firstName": "Rachel",
            "lastName": "Williams"
        },
        "status": false,
        "createDate": "2024-01-02 11:00:00",
        "registerStatus": true
    },
    {
        "id": 3,
        "title": "Data Privacy",
        "description": "Understanding the privacy implications of data collection and analysis.",
        "staff": {
            "id": 2,
            "username": "mjones",
            "password": "123456",
            "email": "mjones@gmail.com",
            "role": "STAFF",
            "firstName": "Michael",
            "lastName": "Jones"
        },
        "status": false,
        "createDate": "2024-01-03 12:00:00",
        "registerStatus": false
    },
    {
        "id": 4,
        "title": "Cybersecurity",
        "description": "Investigating the latest cybersecurity threats and defenses.",
        "staff": {
            "id": 2,
            "username": "mjones",
            "password": "123456",
            "email": "mjones@gmail.com",
            "role": "STAFF",
            "firstName": "Michael",
            "lastName": "Jones"
        },
        "status": false,
        "createDate": "2024-01-04 13:00:00",
        "registerStatus": false
    }
]
```

### Get all proposed projects for the staff

This API fetches all the projects proposed by a particular staff member.

- **Endpoint:** `GET /api/staff/{staffId}/proposed-projects`
- **Path Variable:**
    - `staffId`: The ID of the staff member.
- **Request Header:**
    - Authorization: "Bearer {JWT token}"
- **Response:**
    - Status Code:
        - `200`: Successfully retrieved the list of proposed projects.
        - `404`: Staff member not found or no projects proposed by the staff.
    - Response Body: A list of projects proposed by the staff member.
        - `id`: Project id.
        - `title`: Title of the project.
        - `description`: Description of the project.
        - `staff`: Staff who proposed the project.
        - `status`: Status of the project.
        - `createDate`: Propose date of the project.
        - `interestStudents`: List of students who registered interest.
        - `assignedStudent`: Student assigned to the project.

#### **Example:**

- Request:

```shell
curl --location 'http://localhost:8080/api/staff/1/proposed-projects' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlNUQUZGIiwiZXhwIjoxNzExNjcwNjg5LCJ1c2VybmFtZSI6InJ3aWxsaWFtcyJ9.yEm1B94J77gMaV7vNCtx6WbAezoibrFqoUS7PmfpSGI'
```

- Response:

```json
[
  {
    "id": 1,
    "title": "AI in Education",
    "description": "Exploring the use of AI to personalize learning experiences.",
    "staff": {
      "id": 1,
      "username": "rwilliams",
      "password": "123456",
      "email": "rwilliams@gmail.com",
      "role": "STAFF",
      "firstName": "Rachel",
      "lastName": "Williams"
    },
    "status": true,
    "createDate": "2024-01-01 10:00:00",
    "interestStudents": [
      {
        "id": 3,
        "username": "jchen",
        "password": "123456",
        "email": "jchen@gmail.com",
        "role": "STUDENT",
        "firstName": "Jenny",
        "lastName": "Chen"
      },
      {
        "id": 4,
        "username": "jzhang",
        "password": "123456",
        "email": "jzhang@gmail.com",
        "role": "STUDENT",
        "firstName": "Jack",
        "lastName": "Zhang"
      },
      {
        "id": 6,
        "username": "asmith",
        "password": "123456",
        "email": "asmith@gmail.com",
        "role": "STUDENT",
        "firstName": "Alice",
        "lastName": "Smith"
      }
    ],
    "assignedStudent": {
      "id": 3,
      "username": "jchen",
      "password": "123456",
      "email": "jchen@gmail.com",
      "role": "STUDENT",
      "firstName": "Jenny",
      "lastName": "Chen"
    }
  },
  {
    "id": 2,
    "title": "Sustainable Computing",
    "description": "Investigating energy-efficient computing techniques for a sustainable future.",
    "staff": {
      "id": 1,
      "username": "rwilliams",
      "password": "123456",
      "email": "rwilliams@gmail.com",
      "role": "STAFF",
      "firstName": "Rachel",
      "lastName": "Williams"
    },
    "status": false,
    "createDate": "2024-01-02 11:00:00",
    "interestStudents": [
      {
        "id": 3,
        "username": "jchen",
        "password": "123456",
        "email": "jchen@gmail.com",
        "role": "STUDENT",
        "firstName": "Jenny",
        "lastName": "Chen"
      },
      {
        "id": 4,
        "username": "jzhang",
        "password": "123456",
        "email": "jzhang@gmail.com",
        "role": "STUDENT",
        "firstName": "Jack",
        "lastName": "Zhang"
      }
    ],
    "assignedStudent": null
  }
]
```

### Staff propose a project

This API is used by staff to propose a new project.

- **Endpoint:** `POST /api/staff/{staffId}/create-project`
- **Path Variable:**
    - `staffId`: The ID of the staff member.
- **Request Header:**
    - Authorization: "Bearer {JWT token}"
- **Request Body:**
    - `title`: Title of the project. (Required)
    - `description`: Description of the project. (Required)
- **Response:**
    - Status Code:
        - `201`: Project successfully created.
        - `400`: Bad request, missing title or description.
        - `404`: Project not found.
    - Response Body: Details of the created project.
        - `id`: Project id.
        - `title`: Title of the project.
        - `description`: Description of the project.
        - `staff`: Staff who proposed the project.
        - `status`: Status of the project.
        - `createDate`: Propose date of the project.

#### **Example:**

- Request:

```shell
curl --location 'http://localhost:8080/api/staff/1/create-project' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlNUQUZGIiwiZXhwIjoxNzExNjcwNjg5LCJ1c2VybmFtZSI6InJ3aWxsaWFtcyJ9.yEm1B94J77gMaV7vNCtx6WbAezoibrFqoUS7PmfpSGI' \
--data '{
    "title": "Title",
    "description": "Description"
}'
```

- Response:

```json
{
  "id": 5,
  "title": "Deep learning",
  "description": "Object detection",
  "staff": {
    "id": 1,
    "username": "rwilliams",
    "password": "123456",
    "email": "rwilliams@gmail.com",
    "role": "STAFF",
    "firstName": "Rachel",
    "lastName": "Williams"
  },
  "status": false,
  "createDate": "2024-03-22T21:54:10.036+00:00"
}
```

### Staff update a project information

This API allows staff to update the information of an existing project.

- **Endpoint:** `PATCH /api/projects/{projectId}`
- **Path Variable:**
    - `projectId`: The ID of the project to be updated.
- **Request Header:**
    - Authorization: "Bearer {JWT token}"
- **Request Body:**
    - `title`: Title of the project.
    - `description`: Description of the project.
- **Response:**
    - Status Code:
        - `200`: Project information successfully updated.
        - `404`: Project not found.
    - Response Body: Details of the updated project.
        - `id`: Project id.
        - `title`: Title of the project.
        - `description`: Description of the project.
        - `staff`: Staff who proposed the project.
        - `status`: Status of the project.
        - `createDate`: Propose date of the project.

#### **Example:**

- Request:

```shell
curl --location --request PATCH 'http://localhost:8080/api/projects/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlNUQUZGIiwiZXhwIjoxNzExNjcwNjg5LCJ1c2VybmFtZSI6InJ3aWxsaWFtcyJ9.yEm1B94J77gMaV7vNCtx6WbAezoibrFqoUS7PmfpSGI' \
--data '{
        "title": "AI Practice",
        "description": "Exploring the use of AI."
}'
```

- Response:

```json
{
  "id": 1,
  "title": "AI Practice",
  "description": "Exploring the use of AI.",
  "staff": {
    "id": 1,
    "username": "rwilliams",
    "password": "123456",
    "email": "rwilliams@gmail.com",
    "role": "STAFF",
    "firstName": "Rachel",
    "lastName": "Williams"
  },
  "status": true,
  "createDate": "2024-01-01 10:00:00"
}
```

### Staff delete a project

This API is used by staff to delete a project.

- **Endpoint:** `DELETE /api/projects/{projectId}`
- **Path Variable:**
    - `projectId`: The ID of the project to be deleted.
- **Request Header:**
    - Authorization: "Bearer {JWT token}"
- **Response:**
    - Status Code:
        - `200`: Project successfully deleted.
        - `404`: Project not found.
        - `409`: Project already assigned.

#### **Example:**

- Request:

```shell
curl --location --request DELETE 'http://localhost:8080/api/projects/2' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlNUQUZGIiwiZXhwIjoxNzExNjcwNjg5LCJ1c2VybmFtZSI6InJ3aWxsaWFtcyJ9.yEm1B94J77gMaV7vNCtx6WbAezoibrFqoUS7PmfpSGI'
```

- Response(success):

```text
Project deleted successfully.
```

- Response(Failure):

```text
Project not found.

Project already assigned to a student.
```

### Student register interest in a project

This API allows a student to register their interest in an available project.

- **Endpoint:** `POST /api/projects/{projectId}/register-interest`
- **Path Variable:**
    - `projectId`: The ID of the project.
- **Request Header:**
    - Authorization: "Bearer {JWT token}"
- **Response:**
    - Status Code:
        - `200`: Successfully registered interest in the project.
        - `404`: Project not found or user not found.
        - `409`: Interest already registered or user already assigned to another project.

#### **Example:**

- Request:

```shell
curl --location --request POST 'http://localhost:8080/api/projects/3/register-interest' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0Iiwicm9sZSI6IlNUVURFTlQiLCJleHAiOjE3MTE2NzE2MTcsInVzZXJuYW1lIjoianpoYW5nIn0.JLMPv1-f9UgotmFRsSr7QiaN1i3qO4G9OoFolpmsqQQ'
```

- Response(success):

```text
Interest registered successfully.
```

- Response(failure):

```text
Project not found.

User not found.

Interest already registered.

Project already assigned to a student.

Student already assigned to a project.
```

### Student unregister interest in a project

This API allows a student to unregister their interest in a project they previously showed interest in.

- **Endpoint:** `POST /api/projects/{projectId}/unregister-interest`
- **Path Variable:**
    - `projectId`: The ID of the project.
- **Request Header:**
    - Authorization: "Bearer {JWT token}"
- **Response:**
    - Status Code:
        - `200`: Successfully unregistered interest in the project.
        - `404`: Project not found, user not found, or interest record not found.
        - `409`: User already assigned to a project, cannot unregister interest.

#### **Example:**

- Request:

```shell
curl --location --request POST 'http://localhost:8080/api/projects/3/unregister-interest' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0Iiwicm9sZSI6IlNUVURFTlQiLCJleHAiOjE3MTE2NzE2MTcsInVzZXJuYW1lIjoianpoYW5nIn0.JLMPv1-f9UgotmFRsSr7QiaN1i3qO4G9OoFolpmsqQQ'
```

- Response(success):

```text
Unregister interest successfully.
```

- Response(failure):

```text
Project not found.

User not found.

Project already assigned to a student.

Student already assigned to a project.

Interest not registered.

Interest record not found.
```

### Staff approve student interest and assign student to the project

This API is used by staff to approve a student's interest in a project and assign the project to the student.

- **Endpoint:** `POST /api/projects/{projectId}/assign-project`
- **Path Variable:**
    - `projectId`: The ID of the project to assign.
- **Request Header:**
    - Authorization: "Bearer {JWT token}"
- **Request Parameter:**
    - `userId`: The ID of the student to be assigned.
- **Response:**
    - Status Code:
        - `200`: Successfully assigned the project to the student.
        - `404`: Project not found, user not found, or interest record not found.
        - `409`: Project already assigned to another student or student already assigned to another project.

#### **Example:**

- Request:

```shell
curl --location --request POST 'http://localhost:8080/api/projects/2/assign-project?userId=4' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlNUQUZGIiwiZXhwIjoxNzExNjcxNTM0LCJ1c2VybmFtZSI6InJ3aWxsaWFtcyJ9.owi0API3p9244WA_GPYwgKcRRzCoyp_4bMEbO6fFXIg'
```

- Response(success):

```text
Project successfully assigned.
```

- Response(failure):

```text
Project not found.

User not found.

Project already assigned to a student.

Student already assigned to a project.
```
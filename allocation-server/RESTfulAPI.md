# RESTful API Documentation

## Todo: Implement
- [x] user login
- [x] user registration
- [x] get user information
- [x] get student information about the project (assigned status, assigned project of the student)
- [x] update user information
- [x] partial update user information (which includes update password)
- [ ] delete user
- [ ] get all available projects for the student
- [ ] get all propsed projects for the staff
- [ ] staff propose a project
- [ ] staff update project information
- [ ] student register interest in a project
- [ ] staff approve student interest and assign student to the project

## Introduction

This document describes the RESTful API for the **Allocation** Application. The API is designed based on the REST principles to provide HTTP interfaces for the client to interact with the server. 

#### Login
This API is used to authenticate the user and provide a JWT token for the user to access the protected resources.
- **Endpoint**: `POST /api/auth/login`
- **Request Body**: 
  - `username`: The username of the user
  - `password`: The password of the user
- **Response**:
  - **Status Code**: 
    - `200`: The user is successfully authenticated
    - `401`: The user is not authenticated
  - **Response Body**: 
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


#### Register a new user

This API is used to register a new user in the Allocation Application.

- Endpoint: `POST /api/users` (please note not the `/api/users/`)
- Request Body:
  - username: The desired username of the new user. Must be unique. (Required)
  - password: The password for the new user. (Required)
  - role: The role of the new user (STAFF or STUDENT). (Required)
  - firstName: The first name of the new user. (Optional)
  - lastName: The last name of the new user. (Optional)
  - email: The email address of the new user. (Optional)
- Response:
  - Status Code:
    - 200: Registration is successful.
    - 400: Registration failed due to a non-unique username or other bad requests.

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

#### Get user information

- Endpoint: `GET /api/users/{userId}`
- Request Header:
  - Authorization: "Bearer {JWT token}"
- Response:
- Status Code:
  - 200: The user information is successfully retrieved.
  - 403: The user is not authorized to access the resource.
  - 404: The user is not found.
- Response Body: The user information in JSON format.
  - id: The user id.
  - username: The username of the user.
  - role: The role of the user.
  - firstName: The first name of the user.
  - lastName: The last name of the user.
  - email: The email address of the user.
- Example:
- Request:
```
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

#### Update user information(All information)

- Endpoint: `PUT /api/users/{userId}`
- Request Header
- Authorization : "Bearer { JWT token }"
- Request Body:
  - id: The user id. (Required)
  - username: The username of the user. (Optional)
  - password: The password of the user. (Optional)
  - role: The role of the user. (Optional)
  - firstName: The first name of the user. (Optional)
  - lastName: The last name of the user. (Optional)
  - email: The email address of the user. (Optional)
- Response:
- Status Code:
  - 200: The user information is successfully updated.
  - 403: The user is not authorized to access the resource.
  - 404: The user is not found.
  - 400: The request is bad.
- Response Body: the user information in JSON format.
  - id: The user id.
  - username: The username of the user.
  - role: The role of the user.
  - firstName: The first name of the user.
  - lastName: The last name of the user.
  - email: The email address of the user.
- Example:
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

#### Update user information(Partial information)
- Endpoint: `PATCH /api/users/{userId}`
- Request Header
- Authorization : "Bearer { JWT token }"
- Request Body:
  - id: The user id. (Required)
  - username: The username of the user. (Optional)
  - password: The password of the user. (Optional)
  - role: The role of the user. (Optional)
  - firstName: The first name of the user. (Optional)
  - lastName: The last name of the user. (Optional)
  - email: The email address of the user. (Optional)
- Response:
- Status Code:
  - 200: The user information is successfully updated.
  - 403: The user is not authorized to access the resource.
  - 404: The user is not found.
  - 400: The request is bad.
- Response Body: the user information in JSON format.
- Example:
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
#### get student information about the project
- Endpoint: `GET /api/users/{userId}/student-info`
- Request Header
- Authorization : "Bearer { JWT token }"
- Response:
- Status Code:
  - 200: The user information is successfully retrieved.
  - 403: The user is not authorized to access the resource.
  - 404: The user is not found.
  - 400: The request is bad.
  - 500: Internal server error

- Response Body: the user information in JSON format.
  - id: The user id.
  - username: The username of the user.
  - role: The role of the user.
  - firstName: The first name of the user.
  - lastName: The last name of the user.
  - email: The email address of the user.
  - assignedStatus: The status of the student assignment.
  - assignedProject: The project assigned to the student.
- Example:
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
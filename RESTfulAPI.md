# RESTful API Documentation

## Todo: Implement
- [ ] user login
- [ ] user registration
- [ ] get user information
- [ ] user password reset
- [ ] update user information
- [ ] delete user
- [ ] get all available projects for the student
- [ ] get all propsed projects for the staff
- [ ] staff propose a project
- [ ] staff update project information
- [ ] student register interest in a project
- [ ] staff approve student interest and assign student to the project
- [ ] student get assigned project

## Introduction

This document describes the RESTful API for the **Allocation** Application. The API is designed based on the REST principles to provide HTTP interfaces for the client to interact with the server. 

#### Login
This API is used to authenticate the user and provide a JWT token for the user to access the protected resources.
- **Endpoint**: `/api/auth/login`
- **Method**: `POST`
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
  - `user_id`: The user id of the user
  - `user_details`: The user information

- Example:
    - request:
        ```shell
        curl -X POST -H "Content-Type: application/json" -d '{"username": "admin", "password": "admin}http://localhost:8080/api/auth
        ```
    
    - Response:
        ```json
        {
            "msg": "User is successfully authenticated",
            "result": "success",
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJpYXQiOjE1OTYwNzYwMzEsImV4cCI6MTU5NjA3NjA5MX0.",
            "user_id": 1,
            "user_details": {
                "email": "example@example.com",
                "first_name": "admin",
                "last_name": "admin",
                "username": "admin"
            }
        }
        ```

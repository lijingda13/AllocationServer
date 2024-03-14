DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS assigned_list;
DROP TABLE IF EXISTS interest_list;

-- Creation of user table
CREATE TABLE users (
      user_id INT AUTO_INCREMENT PRIMARY KEY,
      username VARCHAR(255),
      role INT, -- 0 for student, 1 for staff
      first_name VARCHAR(255),
      last_name VARCHAR(255),
      password VARCHAR(255)
);

-- Creation of project table
CREATE TABLE projects (
     project_id INT AUTO_INCREMENT PRIMARY KEY,
     staff_user_id INT,
     title VARCHAR(255),
     description TEXT,
     status BOOLEAN, -- 0 for available, 1 for assigned
     create_time TIMESTAMP,
     FOREIGN KEY (staff_user_id) REFERENCES users (user_id)
);

-- Creation of interest list table
CREATE TABLE interest_list (
    project_id INT,
    student_user_id INT,
    FOREIGN KEY (project_id) REFERENCES projects (project_id),
    FOREIGN KEY (student_user_id) REFERENCES users (user_id),
    PRIMARY KEY (project_id, student_user_id)
);

-- Creation of assigned list table
CREATE TABLE assigned_list (
    project_id INT,
    student_user_id INT,
    assign_time TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects (project_id),
    FOREIGN KEY (student_user_id) REFERENCES users (user_id),
    PRIMARY KEY (project_id, student_user_id)
);

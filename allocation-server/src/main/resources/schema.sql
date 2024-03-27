DROP TABLE IF EXISTS interest_record;
DROP TABLE IF EXISTS assign_record;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS users;


-- Creation of user table
CREATE TABLE users (
      id INT AUTO_INCREMENT PRIMARY KEY,
      username VARCHAR(255),
      role INT, -- 0 for student, 1 for staff
      firstname VARCHAR(255),
      lastname VARCHAR(255),
      password VARCHAR(255),
      email VARCHAR(255)
);

-- Creation of project table
CREATE TABLE projects (
     id INT AUTO_INCREMENT PRIMARY KEY,
     staff_user_id INT,
     title VARCHAR(255),
     description TEXT,
     status BOOLEAN, -- false for available, true for assigned
     create_date TIMESTAMP,
     FOREIGN KEY (staff_user_id) REFERENCES users (id)
);

-- Creation of interest list table
CREATE TABLE interest_record (
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT,
    student_user_id INT,
    FOREIGN KEY (project_id) REFERENCES projects (id),
    FOREIGN KEY (student_user_id) REFERENCES users (id)
);

-- Creation of assigned list table
CREATE TABLE assign_record (
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT,
    student_user_id INT,
    assign_date TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects (id),
    FOREIGN KEY (student_user_id) REFERENCES users (id)
);

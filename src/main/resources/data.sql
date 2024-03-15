-- Sample users
INSERT INTO users (username, role, firstname, lastname, password) VALUES
    ('rwilliams', 1, 'Rachel', 'Williams', 'password123'),
    ('mjones', 1, 'Michael', 'Jones', 'password123'),
    ('jchen', 0, 'Jenny', 'Chen', 'password123'),
    ('jzhang', 0, 'Jack', 'Zhang', 'password123'),
    ('jdoe', 0, 'John', 'Doe', 'password123'),
    ('asmith', 0, 'Alice', 'Smith', 'password123');

-- Sample projects
INSERT INTO projects (staff_user_id, title, description, status, create_time) VALUES
    (1, 'AI in Education', 'Exploring the use of AI to personalize learning experiences.', FALSE, '2024-01-01 10:00:00'),
    (1, 'Sustainable Computing', 'Investigating energy-efficient computing techniques for a sustainable future.', FALSE, '2024-01-02 11:00:00'),
    (2, 'Data Privacy', 'Understanding the privacy implications of data collection and analysis.', TRUE, '2024-01-03 12:00:00'),
    (2, 'Cybersecurity', 'Investigating the latest cybersecurity threats and defenses.', TRUE, '2024-01-04 13:00:00');

-- Sample interest list entries
INSERT INTO interested_record (project_id, student_user_id) VALUES
    (1, 3),
    (2, 3),
    (2, 4),
    (3, 5),
    (3, 6),
    (4, 5),
    (1, 6);

-- Sample assigned list entries
INSERT INTO assigned_record (project_id, student_user_id, assign_time) VALUES
    (1, 3, '2024-02-01 12:00:00');

INSERT INTO course (id, name, semester, difficulty)
VALUES
    (1, 'Algorithms', 'Year 2 - Semester B', 5),
    (2, 'Operating Systems', 'Year 2 - Semester B', 4),
    (3, 'Computer Networks', 'Year 2 - Semester B', 4);

INSERT INTO tasks (id, title, description, deadline, estimated_hours, priority, status, course_id)
VALUES
    (1, 'Solve DP exercises', 'Practice dynamic programming before exam', '2026-06-01', 8.0, 5, 'TODO', 1),
    (2, 'Read OS summary', 'Review processes and threads', '2026-06-10', 3.0, 3, 'TODO', 2),
    (3, 'Practice networking questions', 'Review TCP, UDP, ARP, and DHCP', '2026-06-15', 4.0, 4, 'IN_PROGRESS', 3);
INSERT INTO `skills` (`skill_name`) VALUES
('Beginner'),
('Intermediate'),
('Advance'),
('Profession');

INSERT INTO `category` (`category_name`) VALUES
("Pop Art"),
("Simplify"),
("Abstract"),
("Realistic"),
("Surreal");


INSERT INTO `drawing_style` (`drawing_style_name`) VALUES
("3D design"),
("Abstract Design"),
("Minimalist design"),
("Conceptual art - Concept art design"),
("Fun Design"),
("Design illustration"),
("Vintage Design"),
("Natural design");

   INSERT INTO `roles` (`role_name`)
  VALUES
    ("ADMIN"),
    ("STAFF"),
    ("INSTRUCTOR"),
    ("CUSTOMER");

INSERT INTO `users` (`user_id`, `username`, `avatar`, `pwd`, `email`, `mobile_num`, `status`, `skill_id`, `role_id`, `created_at`, `updated_at`, `full_name`)
VALUES
    (1, 'newuser1', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user1@example.com', '1234567890', 'OPEN', 1, 2, '2023-09-19 09:00:00', '2023-09-19 09:00:00', 'newuser1'),
    (2, 'newuser2', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user2@example.com', NULL, 'OPEN', 2, 2, '2023-09-20 10:15:00', '2023-09-20 10:15:00', 'newuser2'),
    (3, 'newuser3', 'avatar_blob_data', '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user3@example.com', '9876543210', 'OPEN', 3, 3, '2023-09-21 14:30:00', '2023-09-21 14:30:00', 'newuser3'),
    (4, 'admin1', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'admin@example.com', '555-123-4567', 'OPEN', 4, 1, '2023-09-22 16:45:00', '2023-09-22 16:45:00', 'admin1'),
    (5, 'newuser4', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user4@example.com', '111-222-3333', 'OPEN', 4, 4, '2023-09-23 18:00:00', '2023-09-23 18:00:00', 'newuser4'),
    (6, 'newuser6', 'avdawlob_data', '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user3@example.com', '9876543210', 'OPEN', 3, 3, '2023-09-21 14:30:00', '2023-09-21 14:30:00', 'newuser6'),
    (7, 'newuser7', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user4@example.com', '111-222-3333', 'OPEN', 4, 3, '2023-09-23 18:00:00', '2023-09-23 18:00:00', 'newuser7'),
    (8, 'newuser8', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user4@example.com', '111-222-3333', 'OPEN', 4, 4, '2023-09-23 18:00:00', '2023-09-23 18:00:00', 'newuser8'),
    (9, 'newuser9', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user4@example.com', '111-222-3333', 'OPEN', 4, 4, '2023-09-23 18:00:00', '2023-09-23 18:00:00', 'newuser9'),
    (10, 'newuser10', NULL, '$2a$12$9nqFV2QiVWyb3g77yytyiO8.Aniid53nrq2dlKWgGwkObLUKEUKRK', 'user4@example.com', '111-222-3333', 'OPEN', 4, 4, '2023-09-23 18:00:00', '2023-09-23 18:00:00', 'newuser101');

INSERT INTO instructors (instructor_id, bio, payment, education)
VALUES
    (3, 'Experienced math tutor specializing in calculus and algebra.', 'PayPal', 'Ph.D. in Mathematics'),
    (6, 'Certified yoga instructor with 10 years of teaching experience.', 'Venmo', 'Yoga Alliance Certification'),
    (7, 'Professional guitar teacher offering lessons in various styles.', 'Cash', 'Bachelor of Music in Guitar Performance');

-- Thêm dữ liệu vào bảng 'courses' liên quan đến học vẽ
INSERT INTO `courses` (`course_id`, `course_title`, `description`, `information`, `skill_id`, `price`, `category_id`, `status`, `image`, `drawing_style_id`, `instructor_id`)
VALUES
    (1, 'Draw Beach And Sand', 'Learn how to draw sea and sand landscapes with watercolor.', 'This course is for beginners.', 1, 49, 1, 'Open', 'https ://storage.googleapis.com/example_test_image/image/courses/co11697775460577', 1, 3),
    (2, 'Draw Tulip Flower', 'Learn how to draw tulips with watercolor pastels.', 'This course is suitable for everyone.', 2, 29, 1, 'Open', 'https: //storage.googleapis.com/example_test_image/image/courses/co11697775460577', 2, 6),
    (3, 'Night Scene Oil Painting', 'Learn night scene oil painting techniques.', 'Need basic oil painting knowledge.', 3, 79, 2, 'Open', 'https:/ /storage.googleapis.com/example_test_image/image/courses/co11697775460577', 3, 7),
    (4, 'Drawing Portraits', 'Learn how to draw artistic portraits with pencil and watercolor.', 'Requires knowledge of basic drawing.', 4, 59, 2, 'Open', 'https ://storage.googleapis.com/example_test_image/image/courses/co11697775460577', 4, 3),
    (5, 'Draw Street Graffiti', 'Learn how to create unique graffiti art.', 'No previous experience needed.', 1, 39, 3, 'Open', 'https://storage.googleapis .com/example_test_image/image/courses/co11697775460577', 5, 7);
-- Thêm dữ liệu vào bảng 'topic' và 'lesson'
-- Thêm dữ liệu vào bảng 'topic' và 'lesson' liên quan đến học vẽ
INSERT INTO `topic` (`topic_id`, `topic_title`, `course_id`, `number`, `status`)
VALUES
    (1, 'Draw the beach', 1, 1, 'Open'),
    (2, 'Drawing a scene of a fisherman', 1, 2, 'Open'),
    (3, 'Draw wave', 1, 3, 'Open'),
    (4, 'Draw tulips', 2, 2, 'Open'),
    (5, 'Draw extra scene', 2, 1, 'Open'),
    (6, 'Night scene oil painting', 3, 1, 'Open'),
    (7, 'Portrait of a model', 4, 1, 'Open'),
    (8, 'Drawing graffiti on the streets at night', 5, 1, 'Open');

INSERT INTO `lesson` (`lesson_id`, `url`, `topic_id`, `name`, `type_file`, `created_at`, `updated_at`, `number`, `status`)
VALUES
    (1, 'lesson1.mp4', 1, 'Drawing sea and sand', 'video', '2023-09-19 10:00:00', '2023-09-19 10:30:00', 1, 'Open'),
    (2, 'lesson2.mp4', 1, 'Drawing the sky and extra details', 'video', '2023-09-19 10:00:00', '2023-09-19 10:30:00', 2, 'Open'),
    (3, 'lesson1.mp4', 2, 'Using watercolor pastels', 'video', '2023-09-19 10:45:00', '2023-09-19 11:15:00', 1 , 'Open'),
    (4, 'lesson2.mp4', 2, 'Drawing people and boats', 'video', '2023-09-19 10:45:00', '2023-09-19 11:15:00', 1, 'Open'),
    (5, 'https://storage.googleapis.com/example_test_image/document/lesson/av3333333', 3, 'Use a combination of learned methods', 'pdf', '2023-09-20 09:30:00', '2023-09-20 10:00:00 ', 1, 'Open'),
    (6, 'lesson1.mp4', 4, 'Portraits and expressions', 'video', '2023-09-21 14:00:00', '2023-09-21 14:30:00', 1 , 'Open'),
    (7, 'lesson2.docx', 5, 'Graffiti tools and techniques', 'document', '2023-09-22 11:00:00', '2023-09-22 11:45:00' , 1, 'Open'),
    (8, 'lesson1.mp4', 6, 'Drawing sky scenes', 'video', '2023-09-23 15:00:00', '2023-09-23 15:45:00', 1, ' Open'),
    (9, 'https://storage.googleapis.com/example_test_image/document/lesson/av3333333', 6, 'Drawing plants with pencil', 'pdf', '2023-09-24 10:30:00', '2023-09-24 11:15:00', 2, 'Open'),
    (10, 'https://storage.googleapis.com/example_test_image/document/lesson/av3333333', 7, 'Image sketching techniques', 'pdf', '2023-09-24 10:30:00', '2023-09-24 11:15:00', 1, 'Open'),
    (11, 'lesson2.mp4', 7, 'Drawing facial details', 'video', '2023-09-24 10:30:00', '2023-09-24 11:15:00', 1, 'Open'),
    (12, 'https://storage.googleapis.com/example_test_image/document/lesson/av3333333', 8, 'Drawing landscapes and sketching people', 'pdf', '2023-09-24 10:30:00', '2023-09-24 11:15:00 ', 1, 'Open');

# Tạo 10 bản ghi
INSERT INTO feedback (feedback_information, status, star, created_at, course_id, user_id)
VALUES
  ('Giảng viên giảng bài rất hay, dễ hiểu và nhiệt tình.', 'OPEN', 5, '2023-09-20 23:12:49', 1, 1),
  ('Nội dung khóa học rất hữu ích, giúp tôi học được nhiều kiến thức mới.', 'OPEN', 5, '2023-09-21 00:00:00', 2, 3),
  ('Tôi có một góp ý nhỏ về khóa học. Tôi nghĩ sẽ tốt hơn nếu giảng viên có thể cung cấp thêm một số bài tập thực hành.', 'OPEN', 4, '2023-09-21 01:00:00', 3, 4),
  ('Tôi rất hài lòng với khóa học này. Tôi đã học được nhiều điều và tôi sẽ giới thiệu khóa học này cho bạn bè của tôi.', 'OPEN', 5, '2023-09-21 02:00:00', 4, 7),
  ('Tôi cảm thấy khóa học này hơi khó, nhưng tôi cũng học được nhiều thứ. Tôi hy vọng giảng viên có thể cung cấp thêm một số tài liệu tham khảo.', 'OPEN', 4, '2023-09-21 03:00:00', 5, 1),
  ('Tôi không hài lòng với khóa học này. Tôi nghĩ nội dung khóa học không được cập nhật và giảng viên không nhiệt tình.', 'OPEN', 2, '2023-09-21 04:00:00', 5, 2),
  ('Tôi hy vọng giảng viên có thể cung cấp thêm một số bài tập nhóm để chúng tôi có thể thực hành cùng nhau.', 'OPEN', 4, '2023-09-21 05:00:00', 1, 3),
  ('Tôi nghĩ khóa học này rất hay và hữu ích. Tôi đã học được nhiều kiến thức mới và tôi có thể áp dụng nó vào công việc của mình.', 'OPEN', 5, '2023-09-21 06:00:00', 1, 4),
  ('Tôi có một thắc mắc về bài tập thực hành. Tôi có thể liên hệ với giảng viên để được giải đáp không?', 'OPEN', 4, '2023-09-21 07:00:00', 4, 2);


INSERT INTO `enroll` (`enroll_id`, `user_id`, `course_id`, `status`)
VALUES
    (1, 5, 1, 'Enroll'),
    (2, 8, 2, 'Enroll'),
    (3, 9, 3, 'Enroll'),
    (4, 10, 4, 'Enroll'),
    (5, 5, 5, 'Enroll'),
    (6, 8, 1, 'Enroll'),
    (7, 9, 2, 'Enroll'),
    (8, 10, 3, 'Enroll'),
    (9, 5, 4, 'Enroll'),
    (10, 8, 5, 'Enroll');


INSERT INTO experience (drawing_style_id, instructor_id)
VALUES
    (1, 3),
    (2, 6),
    (3, 7),
    (4, 3),
    (5, 6),
    (6, 7),
    (7, 3),
    (8, 6),
    (1, 7),
    (2, 3);

INSERT INTO certificates (certificate_id, image, instructor_id, status)
VALUES
    (1, "image", 3, "ACCEPT"),
    (2, "image", 6, "ACCEPT"),
    (3, "image", 7, "ACCEPT"),
    (4, "image", 3, "ACCEPT"),
    (5, "image", 6, "ACCEPT"),
    (6, "image", 7, "ACCEPT"),
    (7, "image", 3, "ACCEPT"),
    (8, "image", 6, "ACCEPT"),
    (9, "image", 7, "ACCEPT"),
    (10, "image", 3, "ACCEPT");

INSERT INTO `process` (`process_id`, `enroll_id`, `lesson_id`, `created_at`, `status`)
VALUES
    (1, 1, 1, NOW(), 'In Progress'),
    (2, 2, 2, NOW(), 'In Progress'),
    (3, 3, 3, NOW(), 'In Progress'),
    (4, 4, 4, NOW(), 'In Progress'),
    (5, 5, 5, NOW(), 'In Progress'),
    (6, 6, 6, NOW(), 'In Progress'),
    (7, 7, 7, NOW(), 'In Progress'),
    (8, 8, 8, NOW(), 'In Progress'),
    (9, 9, 9, NOW(), 'In Progress'),
    (10, 10, 10, NOW(), 'Completed'),
    (11, 1, 2, NOW(), 'In Progress'),
    (12, 2, 12, NOW(), 'In Progress'),
    (13, 3, 3, NOW(), 'In Progress'),
    (14, 4, 4, NOW(), 'In Progress'),
    (15, 5, 5, NOW(), 'In Progress'),
    (16, 6, 1, NOW(), 'In Progress'),
    (17, 7, 2, NOW(), 'In Progress'),
    (18, 8, 3, NOW(), 'In Progress'),
    (19, 9, 4, NOW(), 'In Progress'),
    (20, 10, 12, NOW(), 'Completed');

INSERT INTO `orders` (`order_id`, `price`, `method`, `description`, `user_id`, `course_id`, `created_at`, `updated_at`, `status`)
VALUES
    (1, 100, 'Credit Card', 'Order for Course 1', 5, 1, NOW(), NOW(), 'Completed'),
    (2, 150, 'PayPal', 'Order for Course 2', 8, 2, NOW(), NOW(), 'Completed'),
    (3, 200, 'Credit Card', 'Order for Course 3', 9, 3, NOW(), NOW(), 'Completed'),
    (4, 120, 'PayPal', 'Order for Course 4', 10, 4, NOW(), NOW(), 'Completed'),
    (5, 180, 'Credit Card', 'Order for Course 5', 5, 5, NOW(), NOW(), 'Completed'),
    (6, 110, 'PayPal', 'Order for Course 1', 8, 1, NOW(), NOW(), 'Completed'),
    (7, 160, 'Credit Card', 'Order for Course 2', 9, 2, NOW(), NOW(), 'Completed'),
    (8, 210, 'PayPal', 'Order for Course 3', 10, 3, NOW(), NOW(), 'Completed'),
    (9, 130, 'Credit Card', 'Order for Course 4', 5, 4, NOW(), NOW(), 'Completed'),
    (10, 190, 'PayPal', 'Order for Course 5', 8, 5, NOW(), NOW(), 'Completed'),
    (11, 105, 'Credit Card', 'Order for Course 1', 10, 1, NOW(), NOW(), 'Completed'),
    (12, 155, 'PayPal', 'Order for Course 2', 9, 2, NOW(), NOW(), 'Completed'),
    (13, 215, 'Credit Card', 'Order for Course 3', 8, 3, NOW(), NOW(), 'Completed'),
    (14, 125, 'PayPal', 'Order for Course 4', 5, 4, NOW(), NOW(), 'Completed'),
    (15, 185, 'Credit Card', 'Order for Course 5', 10, 5, NOW(), NOW(), 'Completed'),
    (16, 120, 'Credit Card', 'Order for Course 1', 5, 1, NOW(), NOW(), 'Completed'),
    (17, 170, 'PayPal', 'Order for Course 2', 8, 2, NOW(), NOW(), 'Completed'),
    (18, 220, 'Credit Card', 'Order for Course 3', 9, 3, NOW(), NOW(), 'Completed');

INSERT INTO `drawcourses`.`assignment` (`assignment_id`, `assignment_title`, `topic`, `lesson_id`, `status`, `number`) VALUES ('1', 'a', 'animal', '1', 'Pending', 1);


INSERT INTO `assignment` (`assignment_id`, `assignment_title`, `topic`, `created_at`, `updated_at`, `lesson_id`, `status`, `number`)
VALUES
    (11, 'Assignment 1', 'Introduction to SQL', NOW(), NOW(), 1, 'Pending', 2),
    (2, 'Assignment 2', 'Data Types', NOW(), NOW(), 2, 'Pending', 1),
    (3, 'Assignment 3', 'SQL Queries', NOW(), NOW(), 3, 'Pending', 1),
    (4, 'Assignment 4', 'Database Design', NOW(), NOW(), 4, 'Pending', 1),
    (5, 'Assignment 5', 'Joins and Subqueries', NOW(), NOW(), 5, 'Pending', 1),
    (6, 'Assignment 6', 'numberes and Optimization', NOW(), NOW(), 6, 'Pending', 1),
    (7, 'Assignment 7', 'Transactions', NOW(), NOW(), 7, 'Pending', 1),
    (8, 'Assignment 8', 'Stored Procedures', NOW(), NOW(), 8, 'Pending', 1),
    (9, 'Assignment 9', 'Triggers', NOW(), NOW(), 9, 'Pending', 1),
    (10, 'Assignment 10', 'Normalization', NOW(), NOW(), 10, 'Pending', 1);


INSERT INTO `user_assignment` (`enroll_id`, `assignment_id`, `task_title`, `description`, `image`, `status`, `grade`, `created_at`, `updated_at`)
VALUES
    (1, 11, 'Task 11', 'Write complex SQL queries', 'task11.jpg', 'Pending', NULL, NOW(), NOW()),
    (2, 1, 'Task 12', 'Optimize SQL performance', 'task12.jpg', 'Pending', NULL, NOW(), NOW()),
    (3, 1, 'Task 13', 'Complete the SQL assignment', 'task13.jpg', 'Pending', NULL, NOW(), NOW()),
    (4, 2, 'Task 14', 'Design a database schema', 'task14.jpg', 'Pending', NULL, NOW(), NOW()),
    (5, 3, 'Task 15', 'Write complex SQL queries', 'task15.jpg', 'Pending', NULL, NOW(), NOW()),
    (6, 4, 'Task 16', 'Optimize SQL performance', 'task16.jpg', 'Pending', NULL, NOW(), NOW()),
    (7, 5, 'Task 17', 'Create SQL joins and subqueries', 'task17.jpg', 'Pending', NULL, NOW(), NOW()),
    (8, 6, 'Task 18', 'Implement database numberes', 'task18.jpg', 'Pending', NULL, NOW(), NOW()),
    (9, 7, 'Task 19', 'Manage SQL transactions', 'task19.jpg', 'Pending', NULL, NOW(), NOW()),
    (10, 8, 'Task 20', 'Write stored procedures', 'task20.jpg', 'Pending', NULL, NOW(), NOW());

INSERT INTO report_students (student_id, course_id, message, image)
VALUES
    (8, 1, 'Example report for student 8, course 1', 'image8_1.jpg'),
    (9, 3, 'Example report for student 9, course 3', 'image9_3.jpg'),
    (10, 5, 'Example report for student 10, course 5', 'image10_5.jpg');

INSERT INTO `posts` (`title`, `category_id`, `description`, `reading_time`, `image`, `body`, `user_id`, `status`, `created_at`, `updated_at`)
VALUES
    ('Introduction to Programming', 1, 'Learn the basics of programming.', 30, 'programming_intro.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 1, 'published', '2023-10-26 10:15:00', '2023-10-26 10:20:00'),
    ('Data Structures and Algorithms', 2, 'Explore various data structures and algorithms.', 45, 'data_structures.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 2, 'published', '2023-10-26 11:30:00', '2023-10-26 11:35:00'),
    ('Web Development Basics', 3, 'Get started with web development.', 60, 'web_dev_basics.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 3, 'draft', '2023-10-26 14:45:00', '2023-10-26 14:50:00'),
    ('Machine Learning Fundamentals', 4, 'Learn the basics of machine learning.', 55, 'ml_fundamentals.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 4, 'published', '2023-10-26 16:00:00', '2023-10-26 16:05:00'),
    ('Mobile App Development', 5, 'Build mobile apps for iOS and Android.', 40, 'mobile_app_dev.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 5, 'published', '2023-10-26 18:15:00', '2023-10-26 18:20:00'),
    ('Python Tips and Tricks', 1, 'Discover useful tips and tricks for Python programming.', 20, 'python_tips.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 6, 'published', '2023-10-26 20:30:00', '2023-10-26 20:35:00'),
    ('Database Design Best Practices', 2, 'Learn about best practices for designing efficient databases.', 35, 'db_design_best_practices.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 7, 'published', '2023-10-26 22:45:00', '2023-10-26 22:50:00'),
    ('Responsive Web Design', 3, 'Create websites that adapt to different screen sizes.', 50, 'responsive_web_design.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 8, 'draft', '2023-10-27 08:00:00', '2023-10-27 08:05:00'),
    ('Introduction to Natural Language Processing', 4, 'Explore the basics of Natural Language Processing (NLP).', 60, 'nlp_intro.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 9, 'published', '2023-10-27 10:15:00', '2023-10-27 10:20:00'),
    ('Mobile App UI Design', 5, 'Design visually appealing user interfaces for mobile apps.', 30, 'mobile_app_ui_design.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 10, 'published', '2023-10-27 12:30:00', '2023-10-27 12:35:00');



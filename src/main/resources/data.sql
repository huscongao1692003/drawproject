INSERT INTO `skills` (`skill_name`) VALUES
('Mới bắt đầu'),
('Trung cấp'),
('Nâng cao'),
('Chuyên nghiệp');

INSERT INTO `category` (`category_name`) VALUES
("Pop Art"),
("Tối giản"),
("Trừu tượng"),
("Hiện thực"),
("Siêu thực");


INSERT INTO `rolling_style` (`rolling_style_name`) VALUES
("Thiết kế 3D"),
("Thiết kế trừu tượng"),
("Thiết kế tối giản"),
("Conceptual art - Thiết kế nghệ thuật khái niệm"),
("Thiết kế vui nhộn"),
("Thiêt kế minh họa"),
("Thiết kế Vintage"),
("Thiết kế tự nhsiên");

   INSERT INTO `roles` (`role_name`)
  VALUES
    ("Admin"),
    ("Staff"),
    ("Instructor"),
    ("Customer");

 INSERT INTO `users` (`username`, `avatar`, `pwd`, `email`, `mobile_num`, `status`, `skill_id`, `role_id`, `created_at`, `updated_at`)
    VALUES
      ('newuser1', NULL, '12345', 'user1@example.com', '1234567890', 'OPEN', 1, 2, '2023-09-19 09:00:00', '2023-09-19 09:00:00'),
      ('newuser2', NULL, '12345', 'user2@example.com', NULL, 'OPEN', 2, 2, '2023-09-20 10:15:00', '2023-09-20 10:15:00'),
      ('newuser3', 'avatar_blob_data', 'hashed_password_3', 'user3@example.com', '9876543210', 'OPEN', 3, 3, '2023-09-21 14:30:00', '2023-09-21 14:30:00'),
      ('admin1', NULL, '12345', 'admin@example.com', '555-123-4567', 'OPEN', 4, 1, '2023-09-22 16:45:00', '2023-09-22 16:45:00'),
      ('newuser4', NULL, '12345', 'user4@example.com', '111-222-3333', 'OPEN', 4, 4, '2023-09-23 18:00:00', '2023-09-23 18:00:00'),
      ('newuser6', 'avdawlob_data', 'hashed_password_3', 'user3@example.com', '9876543210', 'OPEN', 3, 3, '2023-09-21 14:30:00', '2023-09-21 14:30:00'),
      ('newuser7', NULL, '12345', 'user4@example.com', '111-222-3333', 'OPEN', 4, 3, '2023-09-23 18:00:00', '2023-09-23 18:00:00');


-- Thêm dữ liệu vào bảng 'courses' liên quan đến học vẽ
INSERT INTO `courses` (`course_title`, `description`, `information`, `skill_id`, `price`, `category_id`, `status`, `image`, `rolling_style_id`, `instructor_id`)
VALUES
  ('Vẽ Biển Và Cát', 'Học cách vẽ phong cảnh biển và cát bằng màu nước.', 'Khóa học này dành cho người mới học vẽ.', 1, 49, 1, 'Open', 'beach.jpg', 1, 10),
  ('Vẽ Hoa Tulip', 'Tìm hiểu cách vẽ hoa tulip bằng pastel màu nước.', 'Khóa học này phù hợp cho tất cả mọi người.', 2, 29, 1, 'Open', 'tulip.jpg', 2, 10),
  ('Tranh Sơn Dầu Cảnh Đêm', 'Học kỹ thuật vẽ tranh sơn dầu cảnh đêm.', 'Cần kiến thức vẽ sơn dầu cơ bản.', 3, 79, 2, 'Open', 'night.jpg', 3, 13),
  ('Vẽ Chân Dung', 'Học cách vẽ chân dung nghệ thuật với bút chì và màu nước.', 'Yêu cầu kiến thức về vẽ căn bản.', 4, 59, 2, 'Open', 'portrait.jpg', 4, 14),
  ('Vẽ Graffiti Đường Phố', 'Học cách tạo nghệ thuật graffiti độc đáo.', 'Không cần kinh nghiệm trước đây.', 1, 39, 3, 'Open', 'graffiti.jpg', 5, 14);

-- Thêm dữ liệu vào bảng 'topic' và 'lesson'
-- Thêm dữ liệu vào bảng 'topic' và 'lesson' liên quan đến học vẽ
INSERT INTO `topic` (`topic_title`, `course_id`)
VALUES
  ('Vẽ bãi biển', 1),
  ('Vẽ cảnh người đánh cá', 1),
  ('Vẽ sóng', 1),
  ('Vẽ hoa tulip', 2),
  ('Vẽ cảnh phụ', 2),
  ('Vẽ tranh sơn dầu cảnh đêm', 3),
  ('Chân dung người mẫu', 4),
  ('Vẽ graffiti phố đêm', 5);

INSERT INTO `lesson` (`url`, `topic_id`, `name`, `type_file`, `created_at`, `updated_at`)
VALUES
  ('lesson1.mp4', 1, 'Vẽ biển và cát', 'video', '2023-09-19 10:00:00', '2023-09-19 10:30:00'),
  ('lesson2.mp4', 1, 'Vẽ trời và chi tiết phụ', 'video', '2023-09-19 10:00:00', '2023-09-19 10:30:00'),
  ('lesson1.mp4', 2, 'Sử dụng pastel màu nước', 'video', '2023-09-19 10:45:00', '2023-09-19 11:15:00'),
  ('lesson2.mp4', 2, 'Vẽ người và thuyền', 'video', '2023-09-19 10:45:00', '2023-09-19 11:15:00'),
  ('lesson1.pdf', 3, 'Sử dụng kết hợp các cách đã học', 'pdf', '2023-09-20 09:30:00', '2023-09-20 10:00:00'),
  ('lesson1.mp4', 4, 'Chân dung và biểu cảm', 'video', '2023-09-21 14:00:00', '2023-09-21 14:30:00'),
  ('lesson2.docx', 5, 'Các công cụ và kỹ thuật graffiti', 'document', '2023-09-22 11:00:00', '2023-09-22 11:45:00'),
  ('lesson1.mp4', 6, 'Vẽ cảnh trời', 'video', '2023-09-23 15:00:00', '2023-09-23 15:45:00'),
  ('lesson2.pdf', 6, 'Vẽ cây cỏ bằng bút chì', 'pdf', '2023-09-24 10:30:00', '2023-09-24 11:15:00'),
  ('lesson1.pdf', 7, 'Kĩ thuật phác họa hình ảnh', 'pdf', '2023-09-24 10:30:00', '2023-09-24 11:15:00'),
  ('lesson2.mp4', 7, 'Vẽ chi tiết trên khuôn mặt', 'video', '2023-09-24 10:30:00', '2023-09-24 11:15:00'),
  ('lesson1.pdf', 8, 'Vẽ phong cảnh và phác hoạt dòng người', 'pdf', '2023-09-24 10:30:00', '2023-09-24 11:15:00');

-- Thêm dữ liệu vào bảng 'orders' và 'order_details' liên quan đến học vẽ
INSERT INTO `orders` (`price`, `method`, `description`, `user_id`, `order_date`)
VALUES
  (50, 'Credit Card', 'Mua khóa học vẽ phong cảnh', 12, '2023-09-28 15:30:00'),
  (80, 'PayPal', 'Mua bộ nét vẽ và sách hướng dẫn về học vẽ', 11, '2023-09-29 09:15:00'),
  (35, 'Credit Card', 'Mua khóa học vẽ chân dung', 13, '2023-09-30 11:00:00'),
  (120, 'PayPal', 'Mua nhiều khóa học vẽ', 14, '2023-10-01 14:30:00'),
  (25, 'Credit Card', 'Mua tài liệu học vẽ điện tử', 11, '2023-10-02 16:45:00');

INSERT INTO `order_details` (`course_id`, `orders_id`)
VALUES
  (1, 1),
  (2, 2),
  (1, 3),
  (4, 4),
  (1, 5),
  (2, 5);

INSERT INTO `drawcourses`.`order_details` (`course_id`, `orders_id`) VALUES ('3', '2');
INSERT INTO `drawcourses`.`order_details` (`course_id`, `orders_id`) VALUES ('3', '3');
INSERT INTO `drawcourses`.`order_details` (`course_id`, `orders_id`) VALUES ('5', '1');
INSERT INTO `drawcourses`.`order_details` (`course_id`, `orders_id`) VALUES ('5', '2');
INSERT INTO `drawcourses`.`order_details` (`course_id`, `orders_id`) VALUES ('4', '2');

# Tạo 10 bản ghi
INSERT INTO feedback (feedback_information, status, star, created_at, course_id, user_id)
VALUES
  ('Giảng viên giảng bài rất hay, dễ hiểu và nhiệt tình.', 'OPEN', 5, '2023-09-20 23:12:49', 1, 12),
  ('Nội dung khóa học rất hữu ích, giúp tôi học được nhiều kiến thức mới.', 'OPEN', 5, '2023-09-21 00:00:00', 2, 13),
  ('Tôi có một góp ý nhỏ về khóa học. Tôi nghĩ sẽ tốt hơn nếu giảng viên có thể cung cấp thêm một số bài tập thực hành.', 'OPEN', 4, '2023-09-21 01:00:00', 3, 14),
  ('Tôi rất hài lòng với khóa học này. Tôi đã học được nhiều điều và tôi sẽ giới thiệu khóa học này cho bạn bè của tôi.', 'OPEN', 5, '2023-09-21 02:00:00', 4, 8),
  ('Tôi cảm thấy khóa học này hơi khó, nhưng tôi cũng học được nhiều thứ. Tôi hy vọng giảng viên có thể cung cấp thêm một số tài liệu tham khảo.', 'OPEN', 4, '2023-09-21 03:00:00', 5, 11),
  ('Tôi không hài lòng với khóa học này. Tôi nghĩ nội dung khóa học không được cập nhật và giảng viên không nhiệt tình.', 'OPEN', 2, '2023-09-21 04:00:00', 5, 12),
  ('Tôi hy vọng giảng viên có thể cung cấp thêm một số bài tập nhóm để chúng tôi có thể thực hành cùng nhau.', 'OPEN', 4, '2023-09-21 05:00:00', 1, 13),
  ('Tôi nghĩ khóa học này rất hay và hữu ích. Tôi đã học được nhiều kiến thức mới và tôi có thể áp dụng nó vào công việc của mình.', 'OPEN', 5, '2023-09-21 06:00:00', 1, 14),
  ('Tôi có một thắc mắc về bài tập thực hành. Tôi có thể liên hệ với giảng viên để được giải đáp không?', 'OPEN', 4, '2023-09-21 07:00:00', 4, 12);


INSERT INTO `drawcourses`.`user_courses` (`user_id`, `course_id`) VALUES
(11, 1), (12, 2), (13, 3), (14, 4), (8, 5), (11, 3), (12, 1), (13, 2), (14, 3), (9, 4),
(11, 4),
(12, 4),
(12, 5),
(13, 1);

INSERT INTO `collection` (`user_id`, `artwork`, `bio`, `certificate`, `experiment`, `status`) VALUES
(10, 'binary_data_here_1', 'Bio for user 10 - Artwork 1', 'Certificate for Artwork 1', 'Experiment details for Artwork 1', 'Open'),
(10, 'binary_data_here_2', 'Bio for user 10 - Artwork 2', 'Certificate for Artwork 2', 'Experiment details for Artwork 2', 'Open'),
(10, 'binary_data_here_3', 'Bio for user 10 - Artwork 3', 'Certificate for Artwork 3', 'Experiment details for Artwork 3', 'Open'),
(13, 'binary_data_here_4', 'Bio for user 13 - Artwork 4', 'Certificate for Artwork 4', 'Experiment details for Artwork 4', 'Open'),
(13, 'binary_data_here_5', 'Bio for user 13 - Artwork 5', 'Certificate for Artwork 5', 'Experiment details for Artwork 5', 'Open'),
(14, 'binary_data_here_6', 'Bio for user 14 - Artwork 6', 'Certificate for Artwork 6', 'Experiment details for Artwork 6', 'Open'),
(14, 'binary_data_here_7', 'Bio for user 14 - Artwork 7', 'Certificate for Artwork 7', 'Experiment details for Artwork 7', 'Open'),
(14, 'binary_data_here_8', 'Bio for user 14 - Artwork 8', 'Certificate for Artwork 8', 'Experiment details for Artwork 8', 'Open'),
(14, 'binary_data_here_9', 'Bio for user 14 - Artwork 9', 'Certificate for Artwork 9', 'Experiment details for Artwork 9', 'Open'),
(14, 'binary_data_here_10', 'Bio for user 14 - Artwork 10', 'Certificate for Artwork 10', 'Experiment details for Artwork 10', 'Open');



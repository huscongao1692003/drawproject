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


-- Thêm dữ liệu vào bảng 'courses' liên quan đến học vẽ
INSERT INTO `courses` (`course_title`, `description`, `information`, `skill_id`, `price`, `category_id`, `status`, `image`, `rolling_style_id`)
VALUES
  ('Vẽ Biển Và Cát', 'Học cách vẽ phong cảnh biển và cát bằng màu nước.', 'Khóa học này dành cho người mới học vẽ.', 1, 49, 1, 'Active', 'beach.jpg', 1),
  ('Vẽ Hoa Tulip', 'Tìm hiểu cách vẽ hoa tulip bằng pastel màu nước.', 'Khóa học này phù hợp cho tất cả mọi người.', 2, 29, 1, 'Active', 'tulip.jpg', 2),
  ('Tranh Sơn Dầu Cảnh Đêm', 'Học kỹ thuật vẽ tranh sơn dầu cảnh đêm.', 'Cần kiến thức vẽ sơn dầu cơ bản.', 3, 79, 2, 'Active', 'night.jpg', 3),
  ('Vẽ Chân Dung', 'Học cách vẽ chân dung nghệ thuật với bút chì và màu nước.', 'Yêu cầu kiến thức về vẽ căn bản.', 4, 59, 2, 'Active', 'portrait.jpg', 4),
  ('Vẽ Graffiti Đường Phố', 'Học cách tạo nghệ thuật graffiti độc đáo.', 'Không cần kinh nghiệm trước đây.', 1, 39, 3, 'Active', 'graffiti.jpg', 5);

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

  INSERT INTO `roles` (`role_name`)
  VALUES
    ("Admin"),
    ("Staff"),
    ("Instructor"),
    ("Customer");

    INSERT INTO `users` (`username`, `avatar`, `pwd`, `email`, `mobile_num`, `status`, `skill_id`, `role_id`, `created_at`, `updated_at`)
    VALUES
      ('newuser1', NULL, '12345', 'user1@example.com', '1234567890', 'Active', 1, 2, '2023-09-19 09:00:00', '2023-09-19 09:00:00'),
      ('newuser2', NULL, '12345', 'user2@example.com', NULL, 'Active', 2, 2, '2023-09-20 10:15:00', '2023-09-20 10:15:00'),
      ('newuser3', 'avatar_blob_data', 'hashed_password_3', 'user3@example.com', '9876543210', 'Active', 3, 3, '2023-09-21 14:30:00', '2023-09-21 14:30:00'),
      ('admin1', NULL, '12345', 'admin@example.com', '555-123-4567', 'Active', 4, 1, '2023-09-22 16:45:00', '2023-09-22 16:45:00'),
      ('newuser4', NULL, '12345', 'user4@example.com', '111-222-3333', 'Active', 4, 4, '2023-09-23 18:00:00', '2023-09-23 18:00:00');


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
  (1, 11),
  (2, 11),
  (1, 13),
  (4, 14),
  (1, 15),
  (2, 15);


use drawcourses;

CREATE TABLE `skills` (
    `skill_id` INT PRIMARY KEY AUTO_INCREMENT,
    `skill_name` VARCHAR(255) NOT NULL
);

CREATE TABLE `roles` (
    `role_id` INT PRIMARY KEY AUTO_INCREMENT,
    `role_name` VARCHAR(255) NOT NULL
);

CREATE TABLE `category` (
    `category_id` INT AUTO_INCREMENT PRIMARY KEY,
    `category_name` VARCHAR(100) NOT NULL
);

CREATE TABLE `rolling_style` (
    `rolling_style_id` INT AUTO_INCREMENT PRIMARY KEY,
    `rolling_style_name` VARCHAR(255) NOT NULL
);

CREATE TABLE `users` (
    `user_id` INT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL,
    `avatar` LONGBLOB,
    `pwd` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `mobile_num` VARCHAR(255),
    `status` VARCHAR(255) NOT NULL,
    `skill_id` INT NOT NULL,
    `role_id` INT NOT NULL,
    `created_at` TIMESTAMP,
    `updated_at` TIMESTAMP,
    FOREIGN KEY (`skill_id`) REFERENCES `skills`(`skill_id`),
    FOREIGN KEY (`role_id`) REFERENCES `roles`(`role_id`)
);

CREATE TABLE `posts` (
    `post_id` INT PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `category_id` INT NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `reading_time` INT NOT NULL,
    `image` LONGBLOB,
    `body` TEXT NOT NULL,
    `user_id` INT NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP,
    `updated_at` TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`category_id`) REFERENCES `category`(`category_id`)
);

CREATE TABLE `courses` (
    `course_id` INT PRIMARY KEY AUTO_INCREMENT,
    `course_title` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `information` TEXT NOT NULL,
    `skill_id` INT NOT NULL,
    `price` INT NOT NULL,
    `category_id` INT NOT NULL,
    `image` LONGBLOB NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    `rolling_style_id` INT NOT NULL,
    `instructor_id` INT NOT NULL,
    `created_at` TIMESTAMP,
    `updated_at` TIMESTAMP,
    FOREIGN KEY (`skill_id`) REFERENCES `skills`(`skill_id`),
    FOREIGN KEY (`category_id`) REFERENCES `category`(`category_id`),
    FOREIGN KEY (`rolling_style_id`) REFERENCES `rolling_style`(`rolling_style_id`),
    FOREIGN KEY (`instructor_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `user_courses` (
    `user_id` INT NOT NULL,
    `course_id` INT NOT NULL,
    PRIMARY KEY (`user_id`, `course_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`)
);

CREATE TABLE `topic` (
    `topic_id` INT PRIMARY KEY AUTO_INCREMENT,
    `topic_title` VARCHAR(255),
    `course_id` INT,
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`)
);

CREATE TABLE `lesson` (
    `lesson_id` INT PRIMARY KEY AUTO_INCREMENT,
    `url` VARCHAR(255),
    `topic_id` INT,
    `name` VARCHAR(255),
    `type_file` VARCHAR(255),
    `created_at` TIMESTAMP,
    `updated_at` TIMESTAMP,
    FOREIGN KEY (`topic_id`) REFERENCES `topic`(`topic_id`)
);


CREATE TABLE `contact_msg` (
    `contact_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `mobile_num` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `message` VARCHAR(255) NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP,
    `updated_at` TIMESTAMP
);

CREATE TABLE `subscribe` (
    `subscribe_id` INT PRIMARY KEY AUTO_INCREMENT,
    `email` VARCHAR(255) NOT NULL
);

CREATE TABLE `orders` (
    `order_id` INT PRIMARY KEY AUTO_INCREMENT,
    `price` INT NOT NULL,
    `method` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255),
    `user_id` INT NOT NULL,
    `order_date` TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `order_details` (
    `course_id` INT NOT NULL,
    `orders_id` INT NOT NULL,
    PRIMARY KEY (`course_id`, `orders_id`),
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`),
    FOREIGN KEY (`orders_id`) REFERENCES `orders`(`order_id`)
);

CREATE TABLE `feedback` (
    `feedback_id` INT PRIMARY KEY AUTO_INCREMENT,
    `feedback_information` VARCHAR(255) NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    `star` INT NOT NULL,
    `created_at` TIMESTAMP,
    `updated_at` TIMESTAMP,
    `course_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `comment` (
    `comment_id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `comment_value` VARCHAR(255) NOT NULL,
    `post_id` INT NULL,
    `course_id` INT NULL,
    `created_at` TIMESTAMP,
    `status` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`post_id`) REFERENCES `posts`(`post_id`),
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`)
);

CREATE TABLE `homework` (
    `homework_id` INT PRIMARY KEY AUTO_INCREMENT,
    `homework_title` VARCHAR(255) NOT NULL,
    `topic` TEXT NOT NULL,
    `created_at` TIMESTAMP,
    `updated_at` TIMESTAMP,
    `course_id` INT NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`)
);


CREATE TABLE `StoreLink` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
	`code` INT NOT NULL,
    `link` VARCHAR(255)
);

CREATE TABLE `collection` (
    `collection_id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `artwork` INT NOT NULL,
    `bio` VARCHAR(255) NULL,
    `certificate` INT NOT NULL,
    `experience` VARCHAR(255),
    `status` VARCHAR(10) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `user_homework` (
    `task_id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `task_title` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255),
    `image` LONGBLOB NOT NULL,
    `created_at` TIMESTAMP,
    `updated_at` TIMESTAMP,
    `homework_id` INT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
    FOREIGN KEY (`homework_id`) REFERENCES `homework`(`homework_id`)
);

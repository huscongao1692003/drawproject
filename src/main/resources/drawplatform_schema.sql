CREATE TABLE `users` (
                         `user_id` integer PRIMARY KEY auto_increment,
                         `username` varchar(255) not null,
                         `avatar` longblob null,
                         `pwd` varchar(255) not null,
                         `email` varchar(255) not null,
                         `mobile_num` varchar(255),
                         `status` varchar(255) not null,
                         `skill_id` integer not null,
                         `role_id` integer not null,
                         `created_at` timestamp,
                         `updated_at` timestamp,
                         FOREIGN KEY (`skill_id`) REFERENCES `skills`(`skill_id`)
);
CREATE TABLE `skills` (
                          `skill_id` integer primary key auto_increment not null,
                          `skill_name` varchar(255) not null

);

CREATE TABLE `posts` (
                         `post_id` integer PRIMARY KEY auto_increment,
                         `title` varchar(255) not null,
                         `category_id` integer not null,
                         `description` varchar(255) not null,
                         `reading_time` integer not null,
                         `image` longblob,
                         `body` text not null,
                         `user_id` integer not null,
                         `status` varchar(255) not null,
                         `created_at` timestamp,
                         `created_by` varchar(255),
                         `updated_at` timestamp,
                         `updated_by` varchar(255)
);

CREATE TABLE `courses` (
                           `course_id` integer PRIMARY KEY auto_increment,
                           `course_title` varchar(255) not null,
                           `description` varchar(255) not null,
                           `information` text not null,
                           `skill_id` integer not null,
                           `price` integer not null,
                           `category_id` integer not null,
                           `image` longblob not null,
                           `status` varchar(255) not null,
                           `created_at` timestamp,
                           `created_by` varchar(255),
                           `updated_at` timestamp,
                           `updated_by` varchar(255),
                           FOREIGN KEY (`skill_id`) REFERENCES `skills`(`skill_id`)
);

CREATE TABLE `rolling_style` (
                                 `rolling_style_id` integer auto_increment primary key,
                                 `rolling_style_name` varchar(255) not null
);

CREATE TABLE `category` (
                            `category_id` integer auto_increment primary key,
                            `category_name` varchar(100) not null
);

CREATE TABLE `user_courses` (
                                `user_id` integer not null,
                                `course_id` integer not null
);

CREATE TABLE `contact_msg` (
                               `contact_id` integer PRIMARY KEY auto_increment,
                               `name` varchar(255) not null,
                               `mobile_num` varchar(255) not null,
                               `email` varchar(255) not null,
                               `message` varchar(255) not null,
                               `status` varchar(255) not null,
                               `created_at` timestamp,
                               `created_by` varchar(255),
                               `updated_at` timestamp,
                               `updated_by` varchar(255)
);

CREATE TABLE `subscribe` (
                             `subscribe_id` integer PRIMARY KEY auto_increment,
                             `email` varchar(255) not null
);

CREATE TABLE `roles` (
                         `role_id` integer PRIMARY KEY auto_increment,
                         `role_name` varchar(255) not null,
                         `created_at` timestamp,
                         `created_by` varchar(255),
                         `updated_at` timestamp,
                         `updated_by` varchar(255)
);

CREATE TABLE `orders` (
                          `order_id` integer PRIMARY KEY auto_increment,
                          `price` integer not null,
                          `corrency` varchar(255) not null,
                          `method` varchar(255) not null,
                          `description` varchar(255) null,
                          `user_id` integer not null,
                          `order_date` timestamp
);

CREATE TABLE `order_details` (
                                 `price` integer,
                                 `course_id` integer not null,
                                 `orders_id` integer not null,
                                 PRIMARY KEY (`course_id`, `orders_id`)
);

CREATE TABLE `feedback` (
                            `feedback_id` integer PRIMARY KEY auto_increment,
                            `feedback_information` varchar(255) not null,
                            `status` varchar(255) not null,
                            `star` integer not null,
                            `created_at` timestamp,
                            `created_by` varchar(255),
                            `updated_at` timestamp,
                            `course_id` integer not null,
                            `user_id` integer not null
);

CREATE TABLE `video_url` (
                             `video_id` integer PRIMARY KEY auto_increment,
                             `video_url` varchar(255) not null,
                             `course_id` integer not null,
                             `created_at` timestamp,
                             `created_by` varchar(255),
                             `updated_at` timestamp,
                             `updated_by` varchar(255)
);

CREATE TABLE `comment` (
                           `comment_id` integer PRIMARY KEY auto_increment,
                           `user_id` integer not null,
                           `comment_value` varchar(255) not null,
                           `post_id` integer not null,
                           `course_id` integer not null,
                           `created_at` timestamp,
                           `updated_at` timestamp,
                           `status` varchar(255) not null
);

CREATE TABLE `homework` (
                            `homework_id` integer PRIMARY KEY auto_increment,
                            `homework_title` varchar(255) not null,
                            `topic` text not null,
                            `created_at` timestamp,
                            `created_by` varchar(255),
                            `updated_at` timestamp,
                            `updated_by` varchar(255),
                            `course_id` integer not null,
                            `status` varchar(255) not null,
                            FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`)
);

CREATE TABLE `collection` (
                              `collection_id` integer PRIMARY KEY  auto_increment,
                              `user_id` integer not null,
                              `artwork` longblob not null,
                              `cetificate` varchar(255) not null,
                              `experiment` varchar(255) not null
);

CREATE TABLE `user_homework` (
                                 `task_id` integer primary key auto_increment,
                                 `user_id` integer not null,
                                 `task_title` varchar(255) not null,
                                 `description` varchar(255) ,
                                 `image` longblob not null,
                                 `created_at` timestamp,
                                 `created_by` varchar(255),
                                 `updated_at` timestamp,
                                 `updated_by` varchar(255),
                                 `homework_id` integer not null
);


ALTER TABLE `courses` ADD FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`);

ALTER TABLE `user_courses` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `user_courses` ADD FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`);

ALTER TABLE `comment` ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`);

ALTER TABLE `comment` ADD FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`);

ALTER TABLE `video_url` ADD FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`);

ALTER TABLE `comment` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `collection` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);


ALTER TABLE `user_homework` ADD FOREIGN KEY (`homework_id`) REFERENCES `homework` (`homework_id`);

ALTER TABLE `user_homework` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `feedback` ADD FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`);

ALTER TABLE `feedback` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `order_details` ADD FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`);

ALTER TABLE `order_details` ADD FOREIGN KEY (`orders_id`) REFERENCES `orders` (`order_id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `posts` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `users` ADD FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`);
ALTER TABLE `skills` ADD FOREIGN KEY (`skill_id`) REFERENCES `users` (`skill_id`);

ALTER TABLE `skills` ADD FOREIGN KEY (`skill_id`) REFERENCES `courses` (`skill_id`);

ALTER TABLE `courses` ADD FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`);

ALTER TABLE `user_courses` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `user_courses` ADD FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`);

ALTER TABLE `comment` ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`);

ALTER TABLE `comment` ADD FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`);

ALTER TABLE `video_url` ADD FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`);

ALTER TABLE `comment` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `collection` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `courses` ADD FOREIGN KEY (`course_id`) REFERENCES `homework` (`course_id`);

ALTER TABLE `user_homework` ADD FOREIGN KEY (`homework_id`) REFERENCES `homework` (`homework_id`);

ALTER TABLE `user_homework` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `feedback` ADD FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`);

ALTER TABLE `feedback` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `order_details` ADD FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`);

ALTER TABLE `order_details` ADD FOREIGN KEY (`orders_id`) REFERENCES `orders` (`order_id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `posts` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `users` ADD FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`);

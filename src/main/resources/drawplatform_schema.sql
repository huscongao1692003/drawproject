CREATE DATABASE drawcourses;

use drawcourses;

CREATE TABLE `skills` (
                          `skill_id` INT PRIMARY KEY AUTO_INCREMENT,
                          `skill_name` VARCHAR(60) NOT NULL
);

CREATE TABLE `roles` (
                         `role_id` INT PRIMARY KEY AUTO_INCREMENT,
                         `role_name` VARCHAR(20) NOT NULL
);

CREATE TABLE `category` (
                            `category_id` INT AUTO_INCREMENT PRIMARY KEY,
                            `category_name` VARCHAR(255) NOT NULL
);

CREATE TABLE `drawing_style` (
                                 `drawing_style_id` INT AUTO_INCREMENT PRIMARY KEY,
                                 `drawing_style_name` VARCHAR(255) NOT NULL
);

CREATE TABLE `users` (
                         `user_id` INT PRIMARY KEY AUTO_INCREMENT,
                         `username` VARCHAR(50) UNIQUE NOT NULL,
                         `full_name` VARCHAR(255) NOT NULL,
                         `avatar` VARCHAR(255),
                         `pwd` VARCHAR(255) NOT NULL,
                         `email` VARCHAR(80) NOT NULL,
                         `mobile_num` VARCHAR(15),
                         `status` VARCHAR(30) NOT NULL,
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
                         `image` VARCHAR(255),
                         `body` TEXT NOT NULL,
                         `user_id` INT NOT NULL,
                         `status` VARCHAR(30) NOT NULL,
                         `created_at` TIMESTAMP,
                         `updated_at` TIMESTAMP,
                         FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
                         FOREIGN KEY (`category_id`) REFERENCES `category`(`category_id`)
);

CREATE TABLE instructors (
                             instructor_id INT PRIMARY KEY NOT NULL,
                             bio VARCHAR(255),
                             payment VARCHAR(255) NOT NULL,
                             education VARCHAR(255) NOT NULL,
                             FOREIGN KEY (`instructor_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `courses` (
                           `course_id` INT PRIMARY KEY AUTO_INCREMENT,
                           `course_title` VARCHAR(255) NOT NULL,
                           `description` VARCHAR(255) NOT NULL,
                           `information` TEXT NOT NULL,
                           `skill_id` INT NOT NULL,
                           `price` INT NOT NULL,
                           `category_id` INT NOT NULL,
                           `image` VARCHAR(255) NOT NULL,
                           `status` VARCHAR(30) NOT NULL,
                           `drawing_style_id` INT NOT NULL,
                           `instructor_id` INT NOT NULL,
                           `created_at` TIMESTAMP,
                           `updated_at` TIMESTAMP,
                           FOREIGN KEY (`skill_id`) REFERENCES `skills`(`skill_id`),
                           FOREIGN KEY (`category_id`) REFERENCES `category`(`category_id`),
                           FOREIGN KEY (`drawing_style_id`) REFERENCES `drawing_style`(`drawing_style_id`),
                           FOREIGN KEY (`instructor_id`) REFERENCES `instructors`(`instructor_id`)
);

CREATE TABLE `enroll` (
                          `enroll_id` INT PRIMARY KEY AUTO_INCREMENT,
                          `user_id` INT NOT NULL,
                          `course_id` INT NOT NULL,
                          `status` VARCHAR(30) NOT NULL,
                          `created_at` TIMESTAMP,
                          `updated_at` TIMESTAMP,
                          FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
                          FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`)
);

CREATE TABLE `topic` (
                         `topic_id` INT PRIMARY KEY AUTO_INCREMENT,
                         `topic_title` VARCHAR(255),
                         `course_id` INT,
                         `index` INT NOT NULL,
                         `status` VARCHAR(30) NOT NULL,
                         FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`)
);

CREATE TABLE `lesson` (
                          `lesson_id` INT PRIMARY KEY AUTO_INCREMENT,
                          `url` VARCHAR(255),
                          `topic_id` INT,
                          `name` VARCHAR(255),
                          `type_file` VARCHAR(255),
                          `index` int NOT NULL,
                           `status` VARCHAR(30) NOT NULL,
                          `created_at` TIMESTAMP,
                          `updated_at` TIMESTAMP,
                          FOREIGN KEY (`topic_id`) REFERENCES `topic`(`topic_id`)
);


CREATE TABLE `contact_msg` (
                               `contact_id` INT PRIMARY KEY AUTO_INCREMENT,
                               `name` VARCHAR(255) NOT NULL,
                               `mobile_num` VARCHAR(15) NOT NULL,
                               `email` VARCHAR(255) NOT NULL,
                               `message` VARCHAR(255) NOT NULL,
                               `status` VARCHAR(30) NOT NULL,
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
                          `method` VARCHAR(60) NOT NULL,
                          `description` VARCHAR(255),
                          `user_id` INT NOT NULL,
                          `course_id` INT NOT NULL,
                          `created_at` TIMESTAMP,
                          `updated_at` TIMESTAMP,
                          `status` VARCHAR(30) NOT NULL,
                          FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
                          FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`)
);

CREATE TABLE `feedback` (
                            `feedback_id` INT PRIMARY KEY AUTO_INCREMENT,
                            `feedback_information` VARCHAR(255) NOT NULL,
                            `status` VARCHAR(30) NOT NULL,
                            `star` INT NOT NULL,
                            `created_at` TIMESTAMP,
                            `updated_at` TIMESTAMP,
                            `course_id` INT NOT NULL,
                            `user_id` INT NOT NULL,
                            FOREIGN KEY (`course_id`) REFERENCES `courses`(`course_id`),
                            FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `assignment` (
                              `assignment_id` INT PRIMARY KEY AUTO_INCREMENT,
                              `assignment_title` VARCHAR(255) NOT NULL,
                              `topic` TEXT NOT NULL,
                              `created_at` TIMESTAMP,
                              `updated_at` TIMESTAMP,
                              `lesson_id` INT NOT NULL,
                              `index` INT NOT NULL,
                              `compulsory` bit DEFAULT FALSE,
                              `status` VARCHAR(255) NOT NULL,
                              FOREIGN KEY (`lesson_id`) REFERENCES `lesson`(`lesson_id`)
);

CREATE TABLE experience (
                            drawing_style_id INT,
                            instructor_id INT,
                            FOREIGN KEY (drawing_style_id) REFERENCES drawing_style(drawing_style_id),
                            FOREIGN KEY (instructor_id) REFERENCES instructors(instructor_id)
);

CREATE TABLE process (
                         process_id INT PRIMARY KEY AUTO_INCREMENT,
                         enroll_id INT,
                         lesson_id INT,
                         created_at TIMESTAMP,
                         updated_at TIMESTAMP,
                         status VARCHAR(30),
                         FOREIGN KEY (enroll_id) REFERENCES enroll(enroll_id),
                         FOREIGN KEY (lesson_id) REFERENCES lesson(lesson_id)
);



CREATE TABLE `user_assignment` (
                                   `task_id` INT PRIMARY KEY AUTO_INCREMENT,
                                   `enroll_id` INT NOT NULL,
                                   `assignment_id` INT NOT NULL,
                                   `task_title` VARCHAR(255) NOT NULL,
                                   `description` VARCHAR(255),
                                   `image` VARCHAR(255) NOT NULL,
                                   `status` VARCHAR(30) NOT NULL,
                                    `grade` INT,
                                    `comment` VARCHAR(255),
                                   `created_at` TIMESTAMP,
                                   `updated_at` TIMESTAMP,
                                   FOREIGN KEY (`enroll_id`) REFERENCES `enroll`(`enroll_id`),
                                   FOREIGN KEY (`assignment_id`) REFERENCES `assignment`(`assignment_id`)
);

CREATE TABLE certificates (
                              certificate_id INT PRIMARY KEY AUTO_INCREMENT,
                              image VARCHAR(255),
                              instructor_id INT,
                              status VARCHAR(30),
                              FOREIGN KEY (instructor_id) REFERENCES instructors(instructor_id)
);

CREATE TABLE artworks (
                          artwork_id INT PRIMARY KEY AUTO_INCREMENT,
                          image VARCHAR(255),
                          category_id INT,
                          instructor_id INT,
                          status VARCHAR(30) NOT NULL ,
                          FOREIGN KEY (instructor_id) REFERENCES instructors(instructor_id),
                          FOREIGN KEY (category_id) REFERENCES category(category_id)
);

CREATE TABLE report_students(
    student_id INT,
    course_id INT,
    message VARCHAR(255),
    image VARCHAR(255),
    status VARCHAR(30),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES users(user_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);
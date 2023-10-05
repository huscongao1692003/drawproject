package com.drawproject.dev.repository;

import com.drawproject.dev.model.UserCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCoursesRepository extends JpaRepository<UserCourses, Integer> {
}

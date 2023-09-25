package com.drawproject.dev.dto;

import com.drawproject.dev.dto.course.CoursePreviewDTO;
import com.drawproject.dev.model.Courses;
import org.modelmapper.AbstractConverter;
public class CoursesToCoursePreviewDTOConverter extends AbstractConverter<Courses, CoursePreviewDTO> {
    @Override
    protected CoursePreviewDTO convert(Courses source) {
        CoursePreviewDTO coursePreviewDTO = new CoursePreviewDTO();
        coursePreviewDTO.setCourseId(source.getCourseId());
        coursePreviewDTO.setCourseTitle(source.getCourseTitle());
        coursePreviewDTO.setPrice(source.getPrice());
        coursePreviewDTO.setStyle(source.getStyle().getRollingStyleName());
        coursePreviewDTO.setCategory(source.getCategory().getCategoryName());
        coursePreviewDTO.setSkill(source.getSkill().getSkillName());
        coursePreviewDTO.setImage(source.getImage());
        // Set other fields as needed

        // Calculate the average star and set it in the DTO
        double averageStar = calculateAverageStar(source);
        coursePreviewDTO.setAverageStar(averageStar);

        // Set the number of lessons
        int numLessons = calculateNumLessons(source);
        coursePreviewDTO.setNumLesson(numLessons);

        return coursePreviewDTO;
    }

    // Implement methods to calculate average star and number of lessons if needed
    private double calculateAverageStar(Courses source) {
        // Implement your logic to calculate the average star
        return source.getAverageStar(); // Replace with your actual calculation
    }

    private int calculateNumLessons(Courses source) {
        // Implement your logic to calculate the number of lessons
        return source.getNumLesson(); // Replace with your actual calculation
    }
}


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

        // Get the average star and set it in Course cua huy
        double averageStar = calculateAverageStar(source);
        coursePreviewDTO.setAverageStar(averageStar);

        // Get the number of lessons
        int numLessons = calculateNumLessons(source);
        coursePreviewDTO.setNumLesson(numLessons);

        return coursePreviewDTO;
    }

    private double calculateAverageStar(Courses source) {
        return source.getAverageStar();
    }

    private int calculateNumLessons(Courses source) {
        return source.getNumLesson();
    }
}


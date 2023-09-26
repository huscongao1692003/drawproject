package com.drawproject.dev.map;

import com.drawproject.dev.dto.course.CourseDTO;
import com.drawproject.dev.dto.course.CoursePreviewDTO;
import com.drawproject.dev.model.Courses;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class MapModel {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static CoursePreviewDTO mapCourseToDTO(Courses course) {
        return modelMapper.map(course, CoursePreviewDTO.class);
    }

    public static List<CoursePreviewDTO> mapListToDTO(List<Courses> courses) {

        if(courses.isEmpty()) {
            return null;
        }

        List<CoursePreviewDTO> list = new ArrayList<>();

        courses.forEach(course -> list.add(mapCourseToDTO(course)));

        return list;
    }

    public static CoursePreviewDTO mapCourseDetailsToDTO(Courses course) {
        return modelMapper.map(course, CoursePreviewDTO.class);
    }

    public static List<CoursePreviewDTO> mapListCourseDetailsToDTO(Page<Courses> courses) {

        List<CoursePreviewDTO> list = new ArrayList<>();

        courses.forEach(course -> list.add(mapCourseDetailsToDTO(course)));

        return list;
    }

}

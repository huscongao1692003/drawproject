package com.drawproject.dev.config;

import com.drawproject.dev.dto.InstructorDetailDTO;
import com.drawproject.dev.dto.course.CoursePreviewDTO;
import com.drawproject.dev.model.Courses;
import com.drawproject.dev.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Create a custom mapping for Set<Courses> to List<CoursePreviewDTO>
        TypeMap<User, InstructorDetailDTO> userToInstructorDetailDTOTypeMap = modelMapper
                .createTypeMap(User.class, InstructorDetailDTO.class);

        userToInstructorDetailDTOTypeMap.addMappings(mapping -> {
            mapping.using(ctx -> {
                Set<Courses> courses = (Set<Courses>) ctx.getSource();
                List<CoursePreviewDTO> coursePreviewDTOs = courses.stream()
                        .map(course -> modelMapper.map(course, CoursePreviewDTO.class))
                        .collect(Collectors.toList());
                return coursePreviewDTOs;
            }).map(User::getCourses, InstructorDetailDTO::setCourses);
        });

        return modelMapper;
    }
}

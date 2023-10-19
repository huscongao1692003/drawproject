package com.drawproject.dev.map;

import com.drawproject.dev.dto.user_assignment.StudentWork;
import com.drawproject.dev.dto.user_assignment.SubmissionDTO;
import com.drawproject.dev.model.UserAssignment;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.ArrayList;
import java.util.List;

public class MapUserAssignment {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        TypeMap<UserAssignment, SubmissionDTO> userAssignmentToDTOTypeMap = modelMapper.createTypeMap(UserAssignment.class, SubmissionDTO.class)
                .addMapping(src -> src.getEnroll().getUser().getFullName(), SubmissionDTO::setFullName)
                .addMapping(src -> src.getEnroll().getCourse().getCourseTitle(), SubmissionDTO::setCourseName)
                .addMapping(src -> src.getAssignment().getLesson().getName(), SubmissionDTO::setLessonName);
    }

    public static StudentWork mapToStudentWork(UserAssignment userAssignment) {
        return modelMapper.map(userAssignment, StudentWork.class);
    }

    public static SubmissionDTO mapToSubmissionDTO(UserAssignment userAssignment) {
        return modelMapper.map(userAssignment, SubmissionDTO.class);
    }

    public static List<SubmissionDTO> mapToSubmissionDTOs(List<UserAssignment> userAssignments) {
        List<SubmissionDTO> list = new ArrayList<>();

        userAssignments.forEach(userAssignment -> list.add(mapToSubmissionDTO(userAssignment)));

        return list;
    }

}

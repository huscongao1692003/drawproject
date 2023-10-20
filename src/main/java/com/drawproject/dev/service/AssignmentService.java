package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.AssignmentDTO;
import com.drawproject.dev.dto.AssignmentResponseDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.map.MapAssignment;
import com.drawproject.dev.model.Assignment;
import com.drawproject.dev.model.Lesson;
import com.drawproject.dev.repository.AssignmentRepository;
import com.drawproject.dev.repository.LessonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LessonRepository lessonRepository;

    public List<AssignmentResponseDTO> getAssignmentDTOs(int lessonId) {
        return MapAssignment.mapAssignmentToDTOs(assignmentRepository.findByLessonLessonIdAndStatusIs(lessonId,
                DrawProjectConstaints.PENDING));
    }

    public ResponseDTO createAssignment(AssignmentDTO assignmentDTO) {
        Assignment assignment = new Assignment();

        modelMapper.map(assignmentDTO, assignment);
        assignment.setLesson(lessonRepository.findById(assignmentDTO.getLessonId()).orElseThrow());
        assignment.setStatus(DrawProjectConstaints.OPEN);

        assignmentRepository.save(assignment);
        return new ResponseDTO(HttpStatus.CREATED, "Assignment saved successfully", assignmentDTO);
    }

    public ResponseDTO updateAssignment(AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRepository.findById(assignmentDTO.getAssignmentId()).orElseThrow();
        modelMapper.map(assignmentDTO, assignment);
        assignment.setLesson(lessonRepository.findById(assignmentDTO.getLessonId()).orElseThrow());
        assignmentRepository.save(assignment);
        return new ResponseDTO(HttpStatus.OK, "Assignment saved successfully", assignmentDTO);
    }

    public ResponseDTO deleteAssignment(int assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow();

        assignment.setStatus(DrawProjectConstaints.CLOSE);
        assignmentRepository.save(assignment);
        return new ResponseDTO(HttpStatus.OK, "Assignment deleted successfully", null);
    }

}

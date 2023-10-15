package com.drawproject.dev.controller;

import com.drawproject.dev.dto.AssignmentDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assignments")
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;

    @PostMapping("")
    public ResponseEntity<ResponseDTO> createAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO) {
        return ResponseEntity.ok().body(assignmentService.createAssignment(assignmentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateAssignment(@PathVariable("id") int assignmentId, @Valid @RequestBody AssignmentDTO assignmentDTO) {
        assignmentDTO.setAssignmentId(assignmentId);
        return ResponseEntity.ok().body(assignmentService.updateAssignment(assignmentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteAssignment(@PathVariable("id") int assignmentId) {
        return ResponseEntity.ok().body(assignmentService.deleteAssignment(assignmentId));
    }

}

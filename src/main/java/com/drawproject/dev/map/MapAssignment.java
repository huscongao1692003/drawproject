package com.drawproject.dev.map;

import com.drawproject.dev.dto.AssignmentResponseDTO;
import com.drawproject.dev.model.Assignment;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MapAssignment {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static AssignmentResponseDTO mapAssignmentToDTO(Assignment assignment) {
        return modelMapper.map(assignment, AssignmentResponseDTO.class);
    }

    public static List<AssignmentResponseDTO> mapAssignmentToDTOs(List<Assignment> assignments) {
        List<AssignmentResponseDTO> list = new ArrayList<>();

        assignments.forEach(assignment -> list.add(mapAssignmentToDTO(assignment)));

        return list;
    }

}

package com.drawproject.dev.map;

import com.drawproject.dev.dto.ProgressDTO;
import com.drawproject.dev.model.Process;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MapProcess {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static ProgressDTO mapProcessToDTO(Process process) {
        return modelMapper.map(process, ProgressDTO.class);
    }

    public static List<ProgressDTO> mapProcessToDTOs(List<Process> processes) {
        List<ProgressDTO> list = new ArrayList<>();

        processes.forEach(process -> list.add(mapProcessToDTO(process)));

        return list;
    }

}

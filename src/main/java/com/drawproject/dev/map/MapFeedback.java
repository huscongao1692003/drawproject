package com.drawproject.dev.map;

import com.drawproject.dev.dto.FeedbackDTO;
import com.drawproject.dev.model.Feedback;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.ArrayList;
import java.util.List;

public class MapFeedback {
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        // Define the mapping configuration for Feedback to FeedbackDTO
        TypeMap<Feedback, FeedbackDTO> feedbackToDTOTypeMap = modelMapper.createTypeMap(Feedback.class, FeedbackDTO.class)
                .addMapping(src -> src.getUser().getUserId(), FeedbackDTO::setUserId)
                .addMapping(src -> src.getUser().getUsername(), FeedbackDTO::setUsername)
                .addMapping(src -> src.getUser().getAvatar(), FeedbackDTO::setAvatar);

    }

    public static FeedbackDTO mapFeedbackToDTO(Feedback feedback) {
        System.out.println(feedback.getUser().getUsername());

        return modelMapper.map(feedback, FeedbackDTO.class);
    }

    public static List<FeedbackDTO> mapListFeedbackToDTO(List<Feedback> feedbacks) {
        List<FeedbackDTO> feedbackDTOs = new ArrayList<>();

        feedbacks.forEach(feedback -> feedbackDTOs.add((mapFeedbackToDTO(feedback))));

        return feedbackDTOs;

    }

}

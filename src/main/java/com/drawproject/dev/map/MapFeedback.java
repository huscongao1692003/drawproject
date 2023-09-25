package com.drawproject.dev.map;

import com.drawproject.dev.dto.FeedbackDTO;
import com.drawproject.dev.model.Feedback;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MapFeedback {
    private static final ModelMapper modelMapper = new ModelMapper();

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

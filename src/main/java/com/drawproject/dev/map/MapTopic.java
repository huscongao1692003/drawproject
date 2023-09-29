package com.drawproject.dev.map;

import com.drawproject.dev.dto.TopicDTO;
import com.drawproject.dev.model.Topic;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.ArrayList;
import java.util.List;

public class MapTopic {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static TopicDTO mapTopicToDTO(Topic topic) {
        return modelMapper.map(topic, TopicDTO.class);
    }

    public static List<TopicDTO> mapTopicpsToDTOs(List<Topic> topics) {

        List<TopicDTO> list = new ArrayList<>();

        topics.forEach(topic -> list.add(mapTopicToDTO(topic)));

        return list;
    }

    public static Topic mapDTOtoTopic(TopicDTO topicDTO) {
        return modelMapper.map(topicDTO, Topic.class);
    }

    public static List<Topic> mapDTOsToTopics(List<TopicDTO> topicDTOs) {
        List<Topic> list = new ArrayList<>();

        topicDTOs.forEach(topicDTO -> list.add(mapDTOtoTopic(topicDTO)));

        return list;
    }

}

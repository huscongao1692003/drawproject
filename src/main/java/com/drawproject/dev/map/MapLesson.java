package com.drawproject.dev.map;

import com.drawproject.dev.dto.LessonDTO;
import com.drawproject.dev.model.Lesson;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;


public class MapLesson {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static LessonDTO mapLessonToDTO(Lesson lesson) {

        return modelMapper.map(lesson, LessonDTO.class);
    }

    public static List<LessonDTO> mapLessonsToDTOs(List<Lesson> lessons) {

        List<LessonDTO> list = new ArrayList<>();

        lessons.forEach(lesson -> list.add(mapLessonToDTO(lesson)));

        return list;
    }

    public static Lesson mapDTOtoLesson(LessonDTO lessonDTO) {
        return modelMapper.map(lessonDTO, Lesson.class);
    }

    public static List<Lesson> mapDTOtoLessons(List<LessonDTO> lessonDTOs) {
        List<Lesson> list = new ArrayList<>();

        lessonDTOs.forEach(lessonDTO -> list.add(mapDTOtoLesson(lessonDTO)));

        return list;
    }

}

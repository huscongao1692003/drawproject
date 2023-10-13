package com.drawproject.dev.map;

import com.drawproject.dev.dto.UserCourseDTO;
import com.drawproject.dev.dto.UserDTO;
import com.drawproject.dev.model.User;
import com.drawproject.dev.service.ProcessService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.ArrayList;
import java.util.List;

public class MapUser {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static UserDTO mapUserToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public static List<UserDTO> mapUsersToDTOs(List<User> users) {
        List<UserDTO> list = new ArrayList<>();

        users.forEach(user -> {
            list.add(mapUserToUserDTO(user));
        });

        return list;
    }

    public static UserCourseDTO mapUserCourseToDTO(User user) {
        return modelMapper.map(user, UserCourseDTO.class);
    }

    public static List<UserCourseDTO> mapUserCourseToDTOs(List<User> users) {
        List<UserCourseDTO> list = new ArrayList<>();

        users.forEach(user -> {
            list.add(mapUserCourseToDTO(user));
        });

        return list;
    }


}

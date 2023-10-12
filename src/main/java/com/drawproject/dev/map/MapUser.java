package com.drawproject.dev.map;

import com.drawproject.dev.dto.UserDTO;
import com.drawproject.dev.model.User;
import org.modelmapper.ModelMapper;

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


}

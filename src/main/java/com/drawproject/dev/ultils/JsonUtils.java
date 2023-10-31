package com.drawproject.dev.ultils;

import com.drawproject.dev.dto.instructor.BestInstructor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static <T> T getJson(String object, Class<T> objectClass) {
        T jsonObject = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonObject = objectMapper.readValue(object, objectClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static BestInstructor objectToBestInstructor(Object[] object) {
        return new BestInstructor(Integer.parseInt(object[0].toString()), object[1].toString(), (object[2] == null) ? "" : object[2].toString(),
                Double.parseDouble(object[3].toString()), Double.parseDouble(object[4].toString()));
    }

    public static List<BestInstructor> objectToListBestInstructor(List<Object[]> objects) {
        List<BestInstructor> list = new ArrayList<>();
        objects.forEach(object -> {
            list.add(objectToBestInstructor(object));
        });
        return list;
    }

}

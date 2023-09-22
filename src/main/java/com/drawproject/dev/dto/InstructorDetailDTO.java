package com.drawproject.dev.dto;

import com.drawproject.dev.model.Collection;
import com.drawproject.dev.model.Courses;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class InstructorDetailDTO {
    private String userName;
    private String avatar;
    private String mobileNum;
    private String email;
    private String skillName;
    private Set<Courses> courses;
    private List<Collection> collectionList;

}

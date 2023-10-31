package com.drawproject.dev.dto.instructor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestInstructor {
    private int instructorId;
    private String fullName;
    private String avatar;
    private double numOfStudent;
    private double numOfStar;

}

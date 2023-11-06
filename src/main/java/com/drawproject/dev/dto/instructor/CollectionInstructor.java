package com.drawproject.dev.dto.instructor;

import lombok.Data;

@Data
public class CollectionInstructor {
    private int userId;
    private String userName;
    private String fullName;
    private String email;
    private String mobileNum;
    private int numOfCertificateOpen;
    private int numOfCertificateClose;
    private int numOfArtWorkOpen;
    private int numOfArtWorkClose;
}

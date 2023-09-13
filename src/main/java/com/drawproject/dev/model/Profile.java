package com.drawproject.dev.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Profile {

    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message="Age must not be blank")
    @NotEmpty
    @Pattern(regexp = "^(4[0-9]|[5-9][0-9]|9[0-9])$", message = "Age must be between 4 and 99")
    private int age;

}

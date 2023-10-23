package com.drawproject.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    private String to;
    private String subject = "Drawcourses Notification";
    private String template;

    public Mail(String to, String template) {
        this.to = to;
        this.template = template;
    }

}

package com.drawproject.dev.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int lessonId;

    private String url;

    @ManyToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "topicId", nullable = false)
    private Topic topic;

    @NotBlank(message = "Name lesson cannot blank")
    private String name;

    private String typeFile;
}

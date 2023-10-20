package com.drawproject.dev.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "lesson")
public class Lesson extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int lessonId;

    private String url;

    private int index;

    private String status;

    @ManyToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "topicId", nullable = false)
    private Topic topic;

    @NotBlank(message = "Name lesson cannot blank")
    private String name;

    private String typeFile;

    @OneToMany(mappedBy = "lesson")
    private Set<Process> processes = new LinkedHashSet<>();
}

package com.drawproject.dev.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Courses extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int courseId;

    private String courseTitle;

    private String description;

    private String information;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,targetEntity = Skill.class)
    @JoinColumn(name = "skill_id", referencedColumnName = "skillId", nullable = false)
    private Skill skill;

    private int price;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, targetEntity = Category.class)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId", nullable = false)
    private Category category;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, targetEntity = Style.class)
    @JoinColumn(name = "drawing_style_id", referencedColumnName = "drawingStyleId", nullable = false)
    private Style style;

    private String image;

    private String status;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "instructorId", nullable = false)
    private Instructor instructor;

    @OneToMany(mappedBy ="courses", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Feedback> feedback;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Topic> topics;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Orders> orders = new HashSet< Orders>();


    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Enroll> enrolls;

    @Transient
    private double averageStar;

    @Transient
    private int numStudent;

    public double getAverageStar() {
        if(this.getFeedback() != null && !this.getFeedback().isEmpty()) {
            double totalStars = 0;
            for (Feedback f : this.getFeedback()) {
                totalStars += f.getStar();
            }
            this.averageStar = totalStars / this.getFeedback().size();
        } else {
            this.averageStar = 0;
        }
        return averageStar;
    }

    public int getNumStudent() {
        if(this.getEnrolls() != null) {
            this.numStudent = this.getEnrolls().size();
        } else {
            this.numStudent = 0;
        }
        return this.numStudent;
    }

    public Courses(String courseTitle, String description, String information, int price, String image, String status) {
        this.courseTitle = courseTitle;
        this.description = description;
        this.information = information;
        this.price = price;
        this.image = image;
        this.status = status;
    }
}

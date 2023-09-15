package com.drawproject.dev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Courses extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int courseId;

    @NotBlank(message = "Title must not be blank")
    @Size(min = 6, message = "Title must be at least 6 characters long")
    private String courseTitle;

    @NotBlank
    private String description;

    @NotBlank
    @Size(min = 100, message = "Title must be at least 100 characters long")
    private String information;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,targetEntity = Skills.class)
    @JoinColumn(name = "skill_id", referencedColumnName = "skillId", nullable = false)
    private Skills skills;

    private int price;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,targetEntity = Category.class)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId",nullable = false)
    private Category category;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,targetEntity = Style.class)
    @JoinColumn(name = "rolling_style_id", referencedColumnName = "rollingStyleId",nullable = false)
    private Style style;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @NotBlank
    private String image;

    private String status;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy ="courses",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Feedback> feedback;
}

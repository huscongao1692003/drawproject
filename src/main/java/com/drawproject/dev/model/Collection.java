package com.drawproject.dev.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
public class Collection {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int collectionId;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private String artwork;

    private String bio;

    private String certificate;

    private String experiment;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
//    private User user;
}

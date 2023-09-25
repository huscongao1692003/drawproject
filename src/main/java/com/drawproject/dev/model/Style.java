package com.drawproject.dev.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rolling_style")
public class Style{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int rollingStyleId;

    private String rollingStyleName;
}

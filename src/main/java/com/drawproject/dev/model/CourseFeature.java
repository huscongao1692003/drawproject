package com.drawproject.dev.model;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseFeature extends Courses {
    @Transient
    private double averageStar;

    @Transient
    private int numLesson;

    

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

    public int getNumLesson() {
        if(this.getTopics() != null) {
            int count = this.getTopics().stream().mapToInt(topic -> (topic != null) ? (topic.getLessons().size()) : 0).sum();
            this.numLesson = count;
        } else {
            this.numLesson = 0;
        }
        return this.numLesson;
    }
}

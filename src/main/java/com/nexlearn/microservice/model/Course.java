package com.nexlearn.microservice.model;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    private Integer credits;
    private Integer maxCapacity;
    private Integer currentEnrollment;

    public Course() {
    }

    public Course(String courseName, Integer credits, Integer maxCapacity) {
        this.courseName = courseName;
        this.credits = credits;
        this.maxCapacity = maxCapacity;
        this.currentEnrollment = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Integer getCurrentEnrollment() {
        return currentEnrollment;
    }

    public void setCurrentEnrollment(Integer currentEnrollment) {
        this.currentEnrollment = currentEnrollment;
    }

    public void incrementEnrollment() {
        this.currentEnrollment++;
    }

    public void decrementEnrollment() {
        this.currentEnrollment--;
    }
}

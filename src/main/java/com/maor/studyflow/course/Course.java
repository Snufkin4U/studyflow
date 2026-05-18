package com.maor.studyflow.course;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String semester;
    private int difficulty;

    public Course() {
    }

    public Course(Long id, String name, String semester, int difficulty) {
        this.id = id;
        this.name = name;
        this.semester = semester;
        this.difficulty = difficulty;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSemester() {
        return semester;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
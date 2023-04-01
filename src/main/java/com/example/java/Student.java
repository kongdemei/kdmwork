package com.example.java;

/**
 * @description:
 * @author: kdm
 * @createDate: 2023/3/31
 */
public class Student {
    private Integer snoid;
    private String name;
    private String gender;

    public Integer getSnoid() {
        return snoid;
    }

    public void setSnoid(Integer snoid) {
        this.snoid = snoid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Student(Integer snoid, String name, String gender) {
        this.snoid = snoid;
        this.name = name;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "snoid=" + snoid +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}

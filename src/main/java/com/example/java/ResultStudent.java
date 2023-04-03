package com.example.java;

import java.util.List;

/**
 * @description:
 * @author: kdm
 * @createDate: 2023/3/31
 */
public class ResultStudent {
   private CustomException exception;
    private Student student;

    public CustomException getException() {
        return exception;
    }

    public void setException(CustomException exception) {
        this.exception = exception;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudents(Student student) {
        this.student = student;
    }

}

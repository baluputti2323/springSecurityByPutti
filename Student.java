package com.example.spring.springSecurity.config.Model.student;


public class Student {
    private int Id;
    private String name;
    private long marks;

    public Student(int id, String name, long marks) {
        Id = id;
        this.name = name;
        this.marks = marks;
    }

    public int getId() {
        return Id;
    }

    public long getMarks() {
        return marks;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(long marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", marks=" + marks +
                '}';
    }
}

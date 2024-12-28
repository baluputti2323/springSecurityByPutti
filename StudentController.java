package com.example.spring.springSecurity.Controller;

import com.example.spring.springSecurity.config.Model.student.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private List<Student> students= new ArrayList<>(List.of(
            new Student(1,"Bala",2345),new Student(2,"Mani",3456)
    ));
    @GetMapping("/students")
    public List<Student> getStudents(){
        return students;
    }
    @GetMapping("/csrf")
    public CsrfToken csrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
// these method is to get crsf code which contains session id , which used for authentication
    }
    @PostMapping("/newStudent")
    public List<Student> addStudent(@RequestBody Student student){
        students.add(student);
        return students;

    }
}

package vn.techmaster.demo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ToString
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Classroom {
//    @Autowired
//    Student student;
//
//    @Autowired
//    Teacher teacher;

    Student student;
    Teacher teacher;

//    public Classroom(Student student, Teacher teacher) {
//        this.student = student;
//        this.teacher = teacher;
//    }
}

package vn.techmaster.demo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@ToString
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {
    String name;

    public Student() {
        name = "Nguyen Van A";
        System.out.println("Student Created!");
    }
}

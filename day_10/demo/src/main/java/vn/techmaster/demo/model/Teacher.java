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
public class Teacher {
    String name;

    public Teacher() {
        name = "Nguyen Van B";
        System.out.println("Teacher Created!");
    }
}

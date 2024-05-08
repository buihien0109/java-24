package vn.techmaster.demo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@EqualsAndHashCode
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    int id;
    String title;
    String author;
    int year;
}

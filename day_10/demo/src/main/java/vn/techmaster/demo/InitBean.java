package vn.techmaster.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.techmaster.demo.model.Student;

@Configuration
public class InitBean {
    public InitBean() {
        System.out.println("InitBean Created!");
    }

    @Bean(name = "student")
    public Student student() {
        System.out.println("Tạo bean Student 1 từ InitBean");
        return new Student("Khuê");
    }

    @Bean(name = "student2")
    public Student student2() {
        System.out.println("Tạo bean Student 2 từ InitBean");
        return new Student("Vũ");
    }
}

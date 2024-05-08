package vn.techmaster.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import vn.techmaster.demo.model.Classroom;
import vn.techmaster.demo.model.Student;
import vn.techmaster.demo.model.Teacher;

@SpringBootApplication
public class DemoAppApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoAppApplication.class, args);

		Student student = context.getBean("student2", Student.class);
		System.out.println(student);

		Teacher teacher = context.getBean(Teacher.class);
		System.out.println(teacher);

		Classroom classroom = context.getBean(Classroom.class);
		System.out.println(classroom);
	}
}

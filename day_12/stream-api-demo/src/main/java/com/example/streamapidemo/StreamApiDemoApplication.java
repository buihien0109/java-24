package com.example.streamapidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class StreamApiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamApiDemoApplication.class, args);

        // C1: Sử dụng class implement
        Greeting vietnam = new Vietnam();
        vietnam.sayHello("Khuê");

        // C2: Sử dụng Anonymous class
        Greeting english = new Greeting() {
            @Override
            public void sayHello(String name) {
                System.out.println("Hello " + name);
            }
        };
        english.sayHello("Thái");

        // C3: Su dụng Lambda Expression
        Greeting china = (name) -> System.out.println("Ní hảo " + name);
        china.sayHello("Vũ");

        List<Integer> nums = new ArrayList<>(List.of(1, 2, 3, 5, 2, 3, 4, 5, 6));
        // Consumer<Integer> consumer = (el) -> System.out.println(el);
        // nums.forEach((el) -> System.out.println(el));
//        nums.sort((n1, n2) -> n2 - n1);
//        nums.forEach((el) -> System.out.println(el));
//        nums.removeIf(num -> num > 4);
//        nums.forEach((el) -> System.out.println(el));

        // 1. x2 giá trị cuả list
//        nums.stream()
//                .map(num -> num * 2)
//                .forEach(num -> System.out.println(num));

        // 2. Lọc ra số chẵn
//        nums.stream()
//                .filter(num -> num % 2 == 0)
//                .forEach(num -> System.out.println(num));

        // 3. Lấy phần tử đầu tiên > 4
//        nums.stream()
//                .filter(num -> num > 4)
//                .findFirst()
//                .ifPresent(num -> System.out.println(num));

        // 4. Lấy danh sách giá trị không trùng nhau
//        nums.stream()
//                .distinct()
//                .forEach(num -> System.out.println(num));

        // 5. Lấy phần tử thứ 3 -> 5; (limit, offet)
//        nums.stream()
//                .skip(2)
//                .limit(3)
//                .forEach(num -> System.out.println(num));

        // 6. Tính tổng các phần tử trong list
//        int sum = nums.stream()
//                .reduce(0, (total, num) -> total + num);
//        System.out.println(sum);

        // 7. Tính trung bình các phần tử trong list
//        double rs = nums.stream()
//                .mapToInt(n -> Integer.valueOf(n))
//                .average().getAsDouble();
//        System.out.println(rs);

        // 8. Kiểm tra list là list các số > 0 hay không
        boolean isGreaterThan0 = nums.stream()
                .allMatch(num -> num > 0);
        System.out.println(isGreaterThan0);
    }
}

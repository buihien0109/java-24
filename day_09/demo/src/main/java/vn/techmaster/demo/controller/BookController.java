package vn.techmaster.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.techmaster.demo.model.Book;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {
    // Http method: GET, POST, PUT, DELETE
    // API URL
    // Request body: Thông tin client gửi lên
    // Response: Thông tin phản hồi server -> client

    private final List<Book> books;
    public BookController() {
        books = new ArrayList<>();
        books.add(new Book(1, "Gone with the wind", "Cuong", 1945));
        books.add(new Book(2, "Chi Dau", "Nam Cao", 1943));
    }

    @GetMapping("/books") // http://localhost:8080/books
    public List<Book> getAllBooks() {
        return books;
    }
}

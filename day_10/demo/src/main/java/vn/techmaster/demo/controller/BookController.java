package vn.techmaster.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.techmaster.demo.model.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
 * @RestController: Được dụng với các API trả về dữ liệu dạng JSON, XML,..
 * @Controller: Được dụng với các API trả về giao diện (template). ngoài ra cũng có thể trả về dữ liệu dạng JSON, XML,..
 * @RestController = @Controller + @ResponseBody
 * ResponseEntity<T>: Class đại diện cho một HTTP Response, cho phép bạn cấu hình status code, headers, và body của response.
 * */

//@RestController
@Controller
@RequestMapping("/books")
public class BookController {
    private final List<Book> books;

    public BookController() {
        books = new ArrayList<>();
        books.add(new Book(1, "Gone with the wind", "Cuong", 1945));
        books.add(new Book(2, "Chi Dau", "Nam Cao", 1943));
        books.add(new Book(3, "The old", "Nguyen Thi C", 1978));
        books.add(new Book(4, "Nha gia kim", "Tran Van B", 1927));
    }

//    @GetMapping("/books") // http://localhost:8080/books
//    @ResponseBody
//    @ResponseStatus(HttpStatus.CREATED)
//    public List<Book> getAllBooks() {
//        return books;
//    }

    // @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(books, HttpStatus.CREATED);
    }

    // http://localhost:8080/books/1, http://localhost:8080/books/2
    // @RequestMapping(method = RequestMethod.GET, value = "/{id}") tuong duong voi @GetMapping("/{id}")
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return new ResponseEntity<>(book, HttpStatus.OK); // 200
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
    }

    @GetMapping("/sortByYear")
    public ResponseEntity<List<Book>> sortBookByYear() {
        // Sap xep theo nam xuat ban giam dan
        books.sort(new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o2.getYear() - o1.getYear();
            }
        });
        // return new ResponseEntity<>(books, HttpStatus.OK);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Book>> searchBook(@PathVariable String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/startYear/{startYear}/endYear/{endYear}")
    public ResponseEntity<List<Book>> getBooksInRangeYear(@PathVariable int startYear,
                                                          @PathVariable int endYear) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getYear() >= startYear && book.getYear() <= endYear) {
                result.add(book);
            }
        }
        return ResponseEntity.ok(result);
    }
}

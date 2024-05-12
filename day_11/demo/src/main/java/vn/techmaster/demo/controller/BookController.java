package vn.techmaster.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.techmaster.demo.model.Book;
import vn.techmaster.demo.service.BookService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = bookService.getBookById(id);
        if(book != null) {
            return ResponseEntity.ok(book); // 200
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
    }

//
//    @GetMapping("/sortByYear")
//    public ResponseEntity<List<Book>> sortBookByYear() {
//        books.sort(new Comparator<Book>() {
//            @Override
//            public int compare(Book o1, Book o2) {
//                return o2.getYear() - o1.getYear();
//            }
//        });
//        return ResponseEntity.ok(books);
//    }
//
//    @GetMapping("/search/{keyword}")
//    public ResponseEntity<List<Book>> searchBook(@PathVariable String keyword) {
//        List<Book> result = new ArrayList<>();
//        for (Book book : books) {
//            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
//                result.add(book);
//            }
//        }
//        return ResponseEntity.ok(result);
//    }
//
//    @GetMapping("/startYear/{startYear}/endYear/{endYear}")
//    public ResponseEntity<List<Book>> getBooksInRangeYear(@PathVariable int startYear,
//                                                          @PathVariable int endYear) {
//        List<Book> result = new ArrayList<>();
//        for (Book book : books) {
//            if (book.getYear() >= startYear && book.getYear() <= endYear) {
//                result.add(book);
//            }
//        }
//        return ResponseEntity.ok(result);
//    }
}

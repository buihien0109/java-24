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
import java.util.List;
import java.util.Optional;


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
        Optional<Book> bookOptional = bookService.getAllBooks()
                .stream()
                .filter(book -> book.getId() == id)
                .findFirst();
        if (bookOptional.isPresent()) {
            return ResponseEntity.ok(bookOptional.get());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/sortByYear")
    public ResponseEntity<List<Book>> sortBookByYear() {
        List<Book> books = bookService.getAllBooks()
                .stream()
                .sorted((b1, b2) -> b2.getYear() - b1.getYear())
                .toList();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Book>> searchBook(@PathVariable String keyword) {
        List<Book> books = bookService.getAllBooks()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/startYear/{startYear}/endYear/{endYear}")
    public ResponseEntity<List<Book>> getBooksInRangeYear(@PathVariable int startYear,
                                                          @PathVariable int endYear) {
        List<Book> books = bookService.getAllBooks()
                .stream()
                .filter(book -> book.getYear() >= startYear && book.getYear() <= endYear)
                .toList();
        return ResponseEntity.ok(books);
    }
}

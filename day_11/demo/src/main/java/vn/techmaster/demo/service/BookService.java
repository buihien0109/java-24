package vn.techmaster.demo.service;

import vn.techmaster.demo.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(int id);

    List<Book> sortBookByYear();

    List<Book> searchBook(String keyword);

    List<Book> getBooksInRangeYear(int startYear, int endYear);
}

package vn.techmaster.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.techmaster.demo.dao.BookDAO;
import vn.techmaster.demo.model.Book;
import vn.techmaster.demo.service.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDAO bookDAO;

    @Override
    public List<Book> getAllBooks() {
        return bookDAO.findAll();
    }

    @Override
    public Book getBookById(int id) {
//        // C1: Tim gian tiep
//        List<Book> books = bookDAO.findAll();
//        for (Book book : books) {
//            if (book.getId() == id) {
//                return book;
//            }
//        }
//        return null;

        // C2: Tim truc tiep trong DB
        return bookDAO.findById(id);
    }

    @Override
    public List<Book> sortBookByYear() {
        return List.of();
    }

    @Override
    public List<Book> searchBook(String keyword) {
        return List.of();
    }

    @Override
    public List<Book> getBooksInRangeYear(int startYear, int endYear) {
        return List.of();
    }
}

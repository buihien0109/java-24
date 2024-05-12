package vn.techmaster.demo.dao.impl;

import org.springframework.stereotype.Repository;
import vn.techmaster.demo.dao.BookDAO;
import vn.techmaster.demo.database.BookDB;
import vn.techmaster.demo.model.Book;

import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {
    @Override
    public List<Book> findAll() {
        return BookDB.books; // select * from books
    }

    @Override
    public Book findById(int id) {
        for (Book book : BookDB.books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null; // select * from books where id = ?;
    }
}

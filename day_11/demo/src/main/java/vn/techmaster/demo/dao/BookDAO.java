package vn.techmaster.demo.dao;

import vn.techmaster.demo.model.Book;

import java.util.List;

public interface BookDAO {
    List<Book> findAll();

    Book findById(int id);
}

package vn.techmaster.demo.utils.file;

import vn.techmaster.demo.model.Book;

import java.util.List;

public interface IFileReader {
    List<Book> readFile(String filePath);
}

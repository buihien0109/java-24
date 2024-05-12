package vn.techmaster.demo.database;

import vn.techmaster.demo.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDB {
    public static List<Book> books = new ArrayList<>(
            List.of(
                    new Book(1, "Gone with the wind", "Cuong", 1945),
                    new Book(2, "Chi Dau", "Nam Cao", 1943),
                    new Book(3, "The old", "Nguyen Thi C", 1978),
                    new Book(4, "Nha gia kim", "Tran Van B", 1927)
            )
    );
}

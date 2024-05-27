package com.example.demothymeleaf.controller;

import com.example.demothymeleaf.PageResponseImpl;
import com.example.demothymeleaf.model.Book;
import com.example.demothymeleaf.model.PageResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {
    private final List<Book> books;

    public WebController() {
        books = new ArrayList<>();
        books.add(new Book(1, "Gone with the wind", "Cuong", 1945));
        books.add(new Book(2, "Chi Dau", "Nam Cao", 1943));
        books.add(new Book(3, "The old", "Nguyen Thi C", 1978));
        books.add(new Book(4, "Nha gia kim", "Tran Van B", 1927));
        books.add(new Book(5, "Sach A", "Cuong", 1974));
        books.add(new Book(6, "Sach B", "Nguyen Thi C", 1935));
        books.add(new Book(7, "Sach C", "Nam Cao", 1952));
        books.add(new Book(8, "Sach D", "Nguyen Thi C", 1965));
        books.add(new Book(9, "Sach E", "Nam Cao", 1972));
        books.add(new Book(10, "Sach F", "Cuong", 1961));
        books.add(new Book(11, "Sach R", "Cuong", 1945));
        books.add(new Book(12, "Sach X", "Nam Cao", 1943));
        books.add(new Book(13, "Sach Y", "Nguyen Thi C", 1978));
        books.add(new Book(14, "Sach Z", "Tran Van B", 1927));
        books.add(new Book(15, "Sach G", "Nam Cao", 1974));
        books.add(new Book(16, "Sach H", "Tran Van B", 1935));
        books.add(new Book(17, "Sach I", "Cuong", 1952));
        books.add(new Book(18, "Sach K", "Cuong", 1965));
        books.add(new Book(19, "Sach M", "Nam Cao", 1972));
        books.add(new Book(20, "Sach L", "Tran Van B", 1961));
        books.add(new Book(21, "Sach S", "Tran Van B", 1969));
    }

    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("book", books.get(0));
        model.addAttribute("books", books);
        return "index";
    }

    /*
     * Dữ liệu trên 1 trang (content) -> tính startIndex, endIndex -> cắt list
     * Có bao nhiêu item trên 1 trang (pageSize) = 5
     * Tổng số trang (totalPages) 5
     * Tổng số phần tử (totalElements)= 21
     * Trang hiện tại (currentPage) bắt đầu từ 1
     * Trang đầu tiên currentPage = 1
     * Trang cuối cùng currentPage = totalPages
     * */

    // localhost:8080/books?page=2&pageSize=10
    // localhost:8080/books
    @GetMapping("/books")
    public String getBookList(Model model,
                              @RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "5") int pageSize) {
        PageResponse<Book> pageData = new PageResponseImpl<>(books, page, pageSize);
        model.addAttribute("pageData", pageData);
        return "book-list";
    }

    @GetMapping("/books/search")
    public String getSearchBook(Model model) {
        return "search-book";
    }

    @GetMapping("/books/{id}")
    public String getBookDetail(Model model, @PathVariable int id) {
        // Tìm sách theo id
        Book book = books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
        model.addAttribute("book", book);

        // Tìm kiếm các sách liên quan (
        // filter - cùng tác giả và khác ID,
        // limit - giới hạn 4 cuốn,
        // sort - sắp xếp theo năm xuất bản giảm dần)
        if (book != null) {
            List<Book> relateBooks = books.stream()
                    .filter(b -> b.getAuthor().equals(book.getAuthor()) && b.getId() != book.getId())
                    .limit(4)
                    .sorted((b1, b2) -> b2.getYear() - b1.getYear())
                    .toList();
            model.addAttribute("relateBooks", relateBooks);
        } else {
            model.addAttribute("relateBooks", new ArrayList<>());
        }
        return "book-detail";
    }

    @GetMapping("/blog")
    public String getBlog() {
        return "admin/blog";
    }
}

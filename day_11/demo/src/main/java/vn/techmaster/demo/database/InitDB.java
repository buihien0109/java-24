package vn.techmaster.demo.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import vn.techmaster.demo.utils.file.IFileReader;

@Configuration
public class InitDB implements CommandLineRunner {
    @Autowired
    private IFileReader fileReader;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Khởi tạo dữ liệu ban đầu cho ứng dụng");
        BookDB.books = fileReader.readFile("books.json");

        System.out.println("Số lượng Books = " + BookDB.books.size());
    }
}

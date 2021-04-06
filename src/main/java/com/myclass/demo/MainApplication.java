package com.myclass.demo;

import com.myclass.entity.Book;
import com.myclass.entity.Role;
import com.myclass.entity.User;
import com.myclass.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan("com.myclass")
@EnableJpaRepositories("com.myclass")
@EntityScan("com.myclass.entity")
public class MainApplication implements CommandLineRunner {
    @Autowired
    private CommonService<Book> bookCommonService;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        if(bookCommonService.findAll().isEmpty()){
            for (int i = 1; i <= 1000; i++) {
                Book book = new Book();
                book.setTitle("Spring Microservices in Action " + i);
                book.setAuthor("John Carnell " + i);
                book.setCoverPhotoUrl(
                        "https://images-na.ssl-images-amazon.com/images/I/417zLTa1uqL._SX397_BO1,204,203,200_.jpg");
                book.setIsbnNumber(1617293989L);
                book.setPrice(2776.00 + i);
                book.setLanguage("English");
                book.setGenre("Technology");
                bookCommonService.saveOrUpdate(book);
            }
        }
    }
}

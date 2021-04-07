package com.myclass.demo;

import com.myclass.entity.Book;
import com.myclass.entity.Role;
import com.myclass.entity.User;
import com.myclass.service.CommonService;
import com.myclass.service.impl.BookServiceImpl;
import com.myclass.service.impl.RoleServiceImpl;
import com.myclass.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan("com.myclass")
@EnableJpaRepositories("com.myclass")
@EntityScan("com.myclass.entity")
public class MainApplication implements CommandLineRunner {
    private final CommonService<Book> bookCommonService;
    private final CommonService<Role> roleService;

    private final CommonService<User> userService;

    public MainApplication(CommonService<Book> bookCommonService,
                           CommonService<Role> roleService,
                           CommonService<User> userService) {
        this.bookCommonService = bookCommonService;
        this.roleService = roleService;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        if(roleService.findAll().isEmpty()) {
            Role role1 = new Role();
            role1.setId(1L);
            role1.setName("admin");
            roleService.saveOrUpdate(role1);
            Role role2= new Role();
            role1.setId(2L);
            role1.setName("user");
            roleService.saveOrUpdate(role2);
        }
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
        if(userService.findAll().isEmpty()){
            User user1 = new User();
            user1.setEmail("test@user.com");
            user1.setName("Test User");
            user1.setMobile("9787456545");
            user1.setRole(roleService.findById(2L).get());
            user1.setPassword(new BCryptPasswordEncoder().encode("testuser"));
            userService.saveOrUpdate(user1);

            User user2 = new User();
            user2.setEmail("test@admin.com");
            user2.setName("Test Admin");
            user2.setMobile("9787456545");
            user2.setRole(roleService.findById(1L).get());
            user2.setPassword(new BCryptPasswordEncoder().encode("testadmin"));
            userService.saveOrUpdate(user2);
        }

    }
}

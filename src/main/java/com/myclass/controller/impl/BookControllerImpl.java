package com.myclass.controller.impl;

import com.myclass.controller.CommonController;
import com.myclass.entity.Book;
import com.myclass.service.CommonService;
import com.myclass.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
public class BookControllerImpl implements CommonController<Book> {
    private final CommonService<Book> bookCommonService;
    private final PageService<Book> bookPageService;

    public BookControllerImpl( CommonService<Book> bookCommonService, PageService<Book> bookPageService) {
        this.bookCommonService = bookCommonService;
        this.bookPageService = bookPageService;
    }

    @Override
    public ResponseEntity<Page<?>> findAllByPageAndSearch(Pageable pageable, String searchText) {
        return new ResponseEntity<>(bookPageService.findAllBookByPageAndSearch(pageable, searchText), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<?>> findAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return new ResponseEntity<>(bookPageService.findAllBookByPage(
                PageRequest.of(
                        pageNumber, pageSize,
                        sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
                )
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        return new ResponseEntity<>(bookCommonService.findById(id).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> save(Book book) {
        return new ResponseEntity<>(bookCommonService.saveOrUpdate(book), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> update(Book book) {
        return new ResponseEntity<>(bookCommonService.saveOrUpdate(book), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        return new ResponseEntity<>(bookCommonService.deleteById(id), HttpStatus.OK);
    }

    @GetMapping("/language")
    public ResponseEntity<Set<String>> findAllLanguages(){
        return new ResponseEntity<>(new TreeSet<>(Arrays.asList("French", "Portuguese", "English", "Russian", "Hindi", "Arabic", "Spanish", "Chinese")), HttpStatus.OK);
    }
    @GetMapping("/genres")
    public ResponseEntity<Set<String>> findAllGenres(){
        return new ResponseEntity<>(new TreeSet<>(Arrays.asList("Technology", "Science", "History", "Fantasy", "Biography", "Horror", "Romance")), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAllBooks(){
        return new ResponseEntity<>(bookCommonService.findAll(),HttpStatus.OK);
    }
}

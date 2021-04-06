package com.myclass.service.impl;

import com.myclass.entity.Book;
import com.myclass.repository.BookRepository;
import com.myclass.service.CommonService;
import com.myclass.service.PageService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BookServiceImpl implements CommonService<Book>, PageService<Book> {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return  bookRepository.findById(id);
    }

    @Override
    public Book saveOrUpdate(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public String deleteById(Long id) {
        JSONObject jsonObject = new JSONObject();
        try {
            bookRepository.deleteById(id);
            jsonObject.put("message", "Book deleted successfully");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    public Page<Book> findAllBookByPageAndSearch(Pageable pageable, String searchText) {
        return bookRepository.findAllBooks(pageable, searchText);
    }

    @Override
    public Page<Book> findAllBookByPage(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}

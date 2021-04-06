package com.myclass.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface CommonController<T> {
    @GetMapping("/search/{searchText}")
    ResponseEntity<Page<?>> findAllByPageAndSearch(Pageable pageable , @PathVariable String searchText);


    @GetMapping
    ResponseEntity<Page<?>>findAll(int pageNumber, int pageSize, String sortBy, String sortDir);


    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable Long id);


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> save(@RequestBody T t);

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> update(@RequestBody T t);


    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id);
}

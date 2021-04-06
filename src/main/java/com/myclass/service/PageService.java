package com.myclass.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PageService<T> {
    Page<T> findAllBookByPageAndSearch(Pageable pageable, String searchText);
    Page<T> findAllBookByPage(Pageable pageable);
 }

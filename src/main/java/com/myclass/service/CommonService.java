package com.myclass.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CommonService<T> {
    List<T> findAll();
    Optional<T> findById(Long id);
    T saveOrUpdate(T t);
    String deleteById(Long id);
}

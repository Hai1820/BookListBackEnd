package com.myclass.repository;

import com.myclass.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    @Query("from Book b where b.title like %:searchText% or " +
            "b.author like %:searchText% or " +
            "b.language like %:searchText% or " +
            "b.genre like %:searchText% order by " +
            "b.price asc ")
    Page<Book> findAllBooks(Pageable pageable, @Param("searchText")String searchText);
}

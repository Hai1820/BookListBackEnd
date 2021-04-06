package com.myclass.repository;

import com.myclass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User where email=:email")
    User findByEmail(@Param("email") String email);
}

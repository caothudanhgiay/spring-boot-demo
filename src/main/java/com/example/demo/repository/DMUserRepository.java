package com.example.demo.repository;


import com.example.demo.enity.DMUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DMUserRepository extends JpaRepository<DMUser, Long> {

    @Query("select u from DMUser u where u.name like %:name% and u.email like %:email%")
    List<DMUser> findByNameAndEmail(@Param("name") String name, @Param("email") String email);

    DMUser findDMUserById(Long id);

    List<DMUser> findAllByName(String name);

    List<DMUser> findAllByNameLike(String name);
}

package com.example.demo.repository;

import com.example.demo.enity.DMUserOAuth2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DMUserOAuth2Repository extends JpaRepository<DMUserOAuth2, Long> {

    Optional<DMUserOAuth2> findByEmail(String email);

}

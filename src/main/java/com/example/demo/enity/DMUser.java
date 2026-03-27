package com.example.demo.enity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="user")
@Data
public class DMUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;
}

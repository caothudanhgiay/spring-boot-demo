package com.example.demo.enity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class DMUserOAuth2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    private String imageUrl;

    @Column(nullable = false)
    private String provider; // e.g., "google", "facebook"

    @Column(nullable = false)
    private String providerId;

    public DMUserOAuth2(String email, String name, String imageUrl, String provider, String providerId) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.provider = provider;
        this.providerId = providerId;
    }
}

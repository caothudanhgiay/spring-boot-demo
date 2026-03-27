package com.example.demo.service;

import com.example.demo.enity.DMUserOAuth2;
import com.example.demo.repository.DMUserOAuth2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private DMUserOAuth2Repository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        // Lấy thông tin tùy theo nhà cung cấp
        String email = getEmail(oauth2User, provider);
        String name = getName(oauth2User, provider);
        String imageUrl = getImageUrl(oauth2User, provider);
        String providerId = oauth2User.getName();

        if (!StringUtils.hasText(email)) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        Optional<DMUserOAuth2> userOptional = userRepository.findByEmail(email);
        DMUserOAuth2 user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.setName(name);
            user.setImageUrl(imageUrl);
        } else {
            user = new DMUserOAuth2(email, name, imageUrl, provider, providerId);
        }

        userRepository.save(user);

        return oauth2User;
    }

    private String getEmail(OAuth2User oauth2User, String provider) {
        String email = oauth2User.getAttribute("email");
        // GitHub có thể không trả về email, cần kiểm tra
        if (!StringUtils.hasText(email) && "github".equalsIgnoreCase(provider)) {
            // Có thể gọi API khác của GitHub để lấy email nếu cần,
            // nhưng hiện tại chúng ta sẽ coi nó là bắt buộc phải có trong scope
            return oauth2User.getAttribute("login") + "@users.noreply.github.com"; // Email giả định
        }
        return email;
    }

    private String getName(OAuth2User oauth2User, String provider) {
        if ("github".equalsIgnoreCase(provider)) {
            String name = oauth2User.getAttribute("name");
            return StringUtils.hasText(name) ? name : oauth2User.getAttribute("login");
        }
        return oauth2User.getAttribute("name");
    }



    private String getImageUrl(OAuth2User oauth2User, String provider) {
        if ("github".equalsIgnoreCase(provider)) {
            return oauth2User.getAttribute("avatar_url");
        }
        return oauth2User.getAttribute("picture");
    }
}

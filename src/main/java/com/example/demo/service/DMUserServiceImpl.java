package com.example.demo.service;

import com.example.demo.enity.DMUser;
import com.example.demo.repository.DMUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DMUserServiceImpl implements DMUserService {

    @Autowired
    DMUserRepository dmUserRepository;

    @Override
    public DMUser getUserById(Long id) {
        return dmUserRepository.findDMUserById(id);
    }

    @Override
    public List<DMUser> getUsersByName(String name) {
        return dmUserRepository.findAllByNameLike(name);
    }

    @Override
    public List<DMUser> getUsersByNameAndEmail(String name, String email) {
        return dmUserRepository.findByNameAndEmail(name, email);
    }
}

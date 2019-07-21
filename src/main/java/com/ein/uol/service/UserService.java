package com.ein.uol.service;

import com.ein.uol.exception.UserNotFoundException;
import com.ein.uol.model.User;
import com.ein.uol.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserById(Integer userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found.");
        }
        return user.get();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User updateUser(User user) { // TODO: Não esta atualizando direito
        User foundUser = findUserById(user.getId());
        foundUser = user;
        return userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        findUserById(userId);
        userRepository.deleteById(userId);
    }
}
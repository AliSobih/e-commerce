package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.dto.UserDTO;
import com.ecomerce.my.ECommerce.project.dto.UserResponse;
import com.ecomerce.my.ECommerce.project.entity.Cart;
import com.ecomerce.my.ECommerce.project.entity.User;
import com.ecomerce.my.ECommerce.project.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepo userRepo;
    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public User getUser() {
        return this.findUser();
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        User user = this.findUser();
        if (userDTO.getFirstName() != null) {
            user.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }
        if (userDTO.getMobileNumber() != null) {
            user.setPhoneNumber(userDTO.getMobileNumber());
        }
        userRepo.save(user);
        return user;
    }

    @Override
    public boolean deleteUser() {
        userRepo.delete(this.findUser());
        return true;
    }

    @Override
    public Cart getCart() {
        User user = findUser();
        return user.getCart();
    }

    @Override
    public User findUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.getUserByEmail(email);
    }
}

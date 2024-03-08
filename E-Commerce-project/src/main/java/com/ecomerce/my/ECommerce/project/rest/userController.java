package com.ecomerce.my.ECommerce.project.rest;

import com.ecomerce.my.ECommerce.project.Service.UserService;
import com.ecomerce.my.ECommerce.project.dto.UserDTO;
import com.ecomerce.my.ECommerce.project.entity.User;
import com.ecomerce.my.ECommerce.project.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class userController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser() {
        User user = userService.getUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO) {
        User user = userService.updateUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

package com.application.SpringBoot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.application.SpringBoot.DTO.userRequest;
import com.application.SpringBoot.Model.User;
import com.application.SpringBoot.Services.userService;

import org.springframework.web.bind.annotation.PutMapping;

import com.application.SpringBoot.DTO.userResponse;


@RestController
public class userController {

    @Autowired
    private userService UserService;

    @PostMapping("/logins")
    @ResponseStatus(HttpStatus.OK)
    public String Login(@RequestBody userRequest loginRequest){
        return   UserService.login(loginRequest);
    }

    @GetMapping("/")
    public RedirectView home() {
        return new RedirectView("http://localhost:3000/AddUser");
    }

    @PostMapping("/addUser")
    @ResponseStatus(HttpStatus.CREATED)
    public userResponse addUser(@RequestBody userRequest userrequest) {
        return UserService.createUser(userrequest);
    }

    @GetMapping("/getUser")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@RequestParam int id) {
        return UserService.getUserById(id);
    }
    
    @GetMapping("/getAllUsers")
    public List<userResponse> getAllUsers() {
        return UserService.getAllUsers();
    }
    

    @DeleteMapping("/deleteUser")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@RequestParam int id) {
        return UserService.deleteUser(id); 
    }

    @PutMapping("/updateUser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<userResponse> updateUser(@RequestBody User user ) {
        return ResponseEntity.ok(UserService.updateUser(user));
    }
}

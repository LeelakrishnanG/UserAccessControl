package com.application.SpringBoot.Services;import com.application.SpringBoot.DTO.userResponse;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.SpringBoot.DTO.userRequest;
import com.application.SpringBoot.Model.User;
import com.application.SpringBoot.Repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class userService {

    @Autowired
    private final UserRepo UserRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    LocalDateTime currentDateTime = LocalDateTime.now();

    public userService() {
        this.UserRepository = null;
    }

    public userResponse createUser(userRequest userRequest) {
    
        if (!getuserbyname(userRequest.getUsername())){
        User user=User.builder()
        .username(userRequest.getUsername())
        .password(new BCryptPasswordEncoder(10).encode(userRequest.getPassword()))
        .createdon(currentDateTime.toString())
        .updatedon(currentDateTime.toString())
        .createdBy(SecurityContextHolder.getContext().getAuthentication().getName())
        .build();

        User savedUser=UserRepository.save(user);

        if (savedUser != null){
        userResponse response=userResponse.builder()
        .username(userRequest.getUsername())
        .message("User Created Successfully")
        .id(savedUser.getID())
        .build();

        return response;
        }
    }

    userResponse response=userResponse.builder()
    .username(userRequest.getUsername())
    .message("User Already Present")
    .build();

    return response;
    }


    public Boolean getuserbyname(String username){
        return UserRepository.findByUsername(username)!=null;
    }
    
    public User getUserById(int id) {
        Optional<User> optionalUser = UserRepository.findById(id);
        
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
                return null;
    }

    public List<userResponse> getAllUsers(){
        List <User> userDetails = UserRepository.findAll();
    List<userResponse> userResponses = userDetails.stream()
        .map(user -> userResponse.builder()
            .id(user.getID())
            .username(user.getUsername())
            .build())
        .collect(Collectors.toList());

    return userResponses;
    }

    public String deleteUser(int id){
    if (UserRepository.existsById(id)){
       UserRepository.deleteById(id);
       return ("User : " + id + " deleted successfully");
    }
    else{
        return ("User : " + id + "  is not found");
    }
}

    public userResponse updateUser (User userrequest){

        Optional<User> optionalUser = UserRepository.findById(userrequest.getID());
    
    if (optionalUser.isPresent()) {
        User user = UserRepository.findById(userrequest.getID()).get();
        if(userrequest.getUsername()!=null){
        user.setUsername(userrequest.getUsername());
        user.setUpdatedon(currentDateTime.toString());}
        if(userrequest.getPassword()!=null){
        user.setPassword(new BCryptPasswordEncoder(10).encode(userrequest.getPassword()));
        user.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setUpdatedon(currentDateTime.toString());
        }

        UserRepository.save(user);
        
        userResponse response=userResponse.builder()
        .username(userrequest.getUsername())
        .message("User Added Successfully")
        .build();
        return response;
        }
            return null;
    }

    public String login (userRequest loginRequest){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),  loginRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(loginRequest.getUsername());
        }
        else{
            return "failed";
        }
    }

}
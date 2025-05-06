package com.application.SpringBoot.Controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class loginUserController {

    @GetMapping("/welcomeuser")
    public String welcome(HttpServletRequest request){
        return ("Welcome User"+ request.getSession().getId());
    }

    @GetMapping("/csrf")
    public CsrfToken csrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
}

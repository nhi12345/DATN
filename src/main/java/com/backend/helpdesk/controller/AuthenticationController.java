package com.backend.helpdesk.controller;

import com.backend.helpdesk.DTO.Login;
import com.backend.helpdesk.DTO.Token;
import com.backend.helpdesk.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

//    @Secured("ROLE_EMPLOYEES")
    @GetMapping
    public ResponseEntity<?> loginGoogle(@RequestHeader("token-google") String tokenGoogle) throws IOException, GeneralSecurityException {
        String email = authenticationService.getEmailFromTokenUser(tokenGoogle);
        ResponseEntity<Token> token=authenticationService.generateToken(email);
        int b=0;
        return authenticationService.generateToken(email);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login){
        return authenticationService.login(login);
    }
}

package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.Login;
import com.backend.helpdesk.DTO.Token;
import com.backend.helpdesk.common.CommonMethods;
import com.backend.helpdesk.configurations.TokenProvider;
import com.backend.helpdesk.entity.RoleEntity;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.exception.UserException.EmailUserIsNotMatch;
import com.backend.helpdesk.repository.RoleRepository;
import com.backend.helpdesk.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class AuthenticationService {

    private static final JacksonFactory jsonFactory = new JacksonFactory();

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private CommonMethods commonMethods;

    public String getEmailFromTokenUser(String tokenGoogle) throws IOException, GeneralSecurityException {

        GoogleIdToken idToken = GoogleIdToken.parse(jsonFactory, tokenGoogle);
        int a=0;
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String email = payload.getEmail();

            checkForUserRegister(email,
                    (String) payload.get("family_name"),
                    (String) payload.get("given_name"));
            int b=0;
            return email;
        }
        return null;
    }

    public ResponseEntity<Token> generateToken(String email) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        email
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        int c=0;
        return ResponseEntity.ok(new Token(token));
    }

    public void checkForUserRegister(String email, String firstName, String lastName) {

        if (commonMethods.isEmailNovaHub(email)) {
            if (!userRepository.findByEmail(email).isPresent()) {
                saveNewAccount(email, firstName, lastName);
            }
        } else {
            throw new EmailUserIsNotMatch();
        }

    }

    public void saveNewAccount(String email, String firstName, String lastName) {

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(email);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(email));
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        Date date= Calendar.getInstance().getTime();
        userEntity.setStartingDay(date);

        // set role default
        RoleEntity roleEntity = roleRepository.findByName("ROLE_EMPLOYEES").get();
        Set<RoleEntity> roleEntities = new HashSet<RoleEntity>() {
            {
                add(roleEntity);
            }
        };
        userEntity.setRoleEntities(roleEntities);
        userRepository.save(userEntity);
    }

    public ResponseEntity<?> login(Login login) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );
        Optional<UserEntity> user=userRepository.findByEmail(login.getEmail());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new Token(token));
    }


}

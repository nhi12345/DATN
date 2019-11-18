package com.backend.helpdesk.configurations;

import com.backend.helpdesk.entity.RoleEntity;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.repository.RoleRepository;
import com.backend.helpdesk.repository.StatusRepository;
import com.backend.helpdesk.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Configuration
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StatusRepository statusRepository;



    private void addRoleIfMissing(String name) {
        if (roleRepository.findByName(name) == null) {
            roleRepository.save(new RoleEntity(name));
        }
    }

    private void addStatusIfMissing(String name) {
        if (statusRepository.findByName(name) == null) {
            statusRepository.save(new Status(name));
        }
    }

    private void addUserIfMissing(String email, String password, String... roles) {
        if (userRepository.findByEmail(email) == null) {
            UserEntity user = new UserEntity(email, new BCryptPasswordEncoder().encode(password), "firstName", "lastName");
            user.setRoleEntities(new HashSet<>());

            for (String role : roles) {
                user.getRoleEntities().add(roleRepository.findByName(role).get());
            }

            userRepository.save(user);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        addRoleIfMissing("ROLE_ADMIN");
        addRoleIfMissing("ROLE_MANAGE");
        addRoleIfMissing("ROLE_EMPLOYEES");

        addStatusIfMissing("PENDING");
        addStatusIfMissing("APPROVED");
        addStatusIfMissing("REJECTED");

        addUserIfMissing("minhhuynh@novahub.vn", "minhhuynh@novahub.vn", "ROLE_EMPLOYEES", "ROLE_ADMIN", "ROLE_MANAGE");
        if (signingKey == null || signingKey.length() == 0) {
            String jws = Jwts.builder()
                    .setSubject("HelpDesk")
                    .signWith(SignatureAlgorithm.HS256, "helpdeskAPI").compact();

            System.out.println("Use this jwt key:");
            System.out.println("jwt-key=" + jws);
        }

    }

    @Value("${jwt-key}")
    private String signingKey;
}
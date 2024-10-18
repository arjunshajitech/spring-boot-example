package com.example.example.user.controller;

import com.example.example.user.dto.Response;
import com.example.example.user.dto.UserRegister;
import com.example.example.user.entity.User;
import com.example.example.user.repository.UserRepository;
import com.example.example.user.services.CustomUserDetails;
import com.example.example.user.services.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    private final SecurityContextHolderStrategy securityContextHolderStrategy =
            SecurityContextHolder.getContextHolderStrategy();


    @PostMapping("/register")
    public Response create(@RequestBody UserRegister request) {

        boolean isUserExists = userRepository.existsByEmail(request.getEmail());
        if (!isUserExists) {
            userRepository.save(
                    new User(request.getEmail(),
                            passwordEncoder.encode(request.getPassword()))
            );
        }

        return new Response();
    }

    @PostMapping("/login")
    public Response login(@RequestBody UserRegister request, HttpServletRequest req, HttpServletResponse res) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, req, res);
        return new Response();
    }

    @GetMapping("/me")
    public User me(Principal principal) {
        return userRepository.findByEmail(principal.getName());
    }
}

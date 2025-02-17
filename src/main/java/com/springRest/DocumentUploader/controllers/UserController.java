package com.springRest.DocumentUploader.controllers;

import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.models.UserDTO;
import com.springRest.DocumentUploader.security.CustomAuthenticationToken;
import com.springRest.DocumentUploader.security.JWTUtil;
import com.springRest.DocumentUploader.security.SecurityUtils;
import com.springRest.DocumentUploader.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public static User getUserFromToken(UserService userService){
        return userService.getUserEntityById(SecurityUtils.getAuthenticatedUserId()).orElseThrow(NotFoundException::new);
    }

    public UserDTO getUserDTOFromToken(UserService userService){
        return userService.getUserById(SecurityUtils.getAuthenticatedUserId()).orElseThrow(NotFoundException::new);
    }

    @PostMapping("/user/login")
    public String login(@RequestBody UserDTO userDTO){
        UserDTO user = userService.getUserByUsername(userDTO.getUsername())
                .orElseThrow(() -> new BadCredentialsException("User not found"));
        if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())){
            return jwtUtil.generateToken(user.getId());
        }
        throw new BadCredentialsException("Password incorrect");
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserDTO> register(@Validated @RequestBody UserDTO user){
        UserDTO userSaved = userService.register(user);
        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/user/"+userSaved.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/id/{id}")
    public UserDTO getUserById(@PathVariable("id") UUID id){
        return userService.getUserById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(value = "/user/details")
    public UserDTO getUserDetails(){

        return getUserDTOFromToken(userService);
    }

    @GetMapping(value = "/user/username/{username}")
    public User getUserByUsername(@PathVariable("username") String username){
        return userService.getUserEntityByUsername(username).orElseThrow(NotFoundException::new);
    }


}

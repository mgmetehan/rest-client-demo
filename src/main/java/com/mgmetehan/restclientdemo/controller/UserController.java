package com.mgmetehan.restclientdemo.controller;

import com.mgmetehan.restclientdemo.dto.request.UserRequest;
import com.mgmetehan.restclientdemo.dto.response.UserCreateResponse;
import com.mgmetehan.restclientdemo.dto.response.UserDataResponse;
import com.mgmetehan.restclientdemo.dto.response.UserResponse;
import com.mgmetehan.restclientdemo.dto.response.UserUpdateResponse;
import com.mgmetehan.restclientdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public UserResponse getListUsers(@RequestParam(defaultValue = "1") int page) {
        return userService.getListUsers(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataResponse> getSingleUser(@PathVariable int id) {
        var response = userService.getSingleUser(id);
        log.info("getSingleUser() called");
        log.info("Response body: {}", response.getBody());
        log.info("Response headers: {}", response.getHeaders());
        log.info("Response status code: {}", response.getStatusCode());
        return response;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreateResponse createUser(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public UserUpdateResponse updateUser(@PathVariable int id, @RequestBody UserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}

package com.mgmetehan.restclientdemo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgmetehan.restclientdemo.dto.request.UserRequest;
import com.mgmetehan.restclientdemo.dto.response.UserCreateResponse;
import com.mgmetehan.restclientdemo.dto.response.UserDataResponse;
import com.mgmetehan.restclientdemo.dto.response.UserResponse;
import com.mgmetehan.restclientdemo.dto.response.UserUpdateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public UserResponse getListUsers(int page) {
        return restClient.get()
                .uri("/users?page={page}", page)
                .exchange((request, response) -> {
                    if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))) {
                        return objectMapper.readValue(response.getBody(), new TypeReference<>() {
                        });
                    } else {
                        throw new RuntimeException(response.getStatusCode().toString());
                    }
                });
    }


    public ResponseEntity<UserDataResponse> getSingleUser(int id) {
        var response = restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .toEntity(UserDataResponse.class);

        log.info("getSingleUser() called");
        log.info("Response body: {}", response.getBody());
        log.info("Response headers: {}", response.getHeaders());
        log.info("Response status code: {}", response.getStatusCode());

        return response;
    }

    public UserCreateResponse createUser(UserRequest request) {
        return restClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                //.header("Content-Type", "application/json")
                .body(request)
                .retrieve()
                .body(UserCreateResponse.class);
    }

    public UserUpdateResponse updateUser(int id, UserRequest request) {
        return restClient.put()
                .uri("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, response) -> {
                    throw new RuntimeException("4xx error");
                })
                .body(UserUpdateResponse.class);
    }

    public void deleteUser(int id) {
        restClient.delete()
                .uri("/users/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}

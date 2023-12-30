package com.mgmetehan.restclientdemo.service;

import com.mgmetehan.restclientdemo.dto.request.UserRequest;
import com.mgmetehan.restclientdemo.dto.response.UserCreateResponse;
import com.mgmetehan.restclientdemo.dto.response.UserDataResponse;
import com.mgmetehan.restclientdemo.dto.response.UserResponse;
import com.mgmetehan.restclientdemo.dto.response.UserUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RestClient restClient;

    public UserResponse getListUsers(int page) {
        return restClient.get()
                .uri("/users?page={page}", page)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is5xxServerError()) {
                        throw new RuntimeException(response.getStatusCode().toString());
                    } else {
                        return UserResponse.builder()
                                .page(0)
                                .perPage(0)
                                .total(0)
                                .totalPages(0)
                                .build();
                    }
                });
    }

    public ResponseEntity<UserDataResponse> getSingleUser(int id) {
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .toEntity(UserDataResponse.class);
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

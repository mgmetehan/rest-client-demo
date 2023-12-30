package com.mgmetehan.restclientdemo.dto.response;

import com.mgmetehan.restclientdemo.model.Support;
import com.mgmetehan.restclientdemo.model.User;

import java.util.List;

public record UserResponse(
        int page,
        int perPage,
        int total,
        int totalPages,
        List<User> data,
        Support support
) {
}
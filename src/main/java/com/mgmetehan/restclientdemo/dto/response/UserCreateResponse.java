package com.mgmetehan.restclientdemo.dto.response;

import java.time.LocalDateTime;

public record UserCreateResponse(
        String name,
        String job,
        String id,
        LocalDateTime createdAt
) {
}
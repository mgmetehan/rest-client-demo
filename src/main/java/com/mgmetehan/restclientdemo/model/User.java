package com.mgmetehan.restclientdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record User(
        int id,
        String email,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        String avatar
) {
}

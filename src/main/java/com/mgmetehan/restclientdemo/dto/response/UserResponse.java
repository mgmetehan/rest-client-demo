package com.mgmetehan.restclientdemo.dto.response;

import com.mgmetehan.restclientdemo.model.Support;
import com.mgmetehan.restclientdemo.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse{
    int page;
    int perPage;
    int total;
    int totalPages;
    List<User> data;
    Support support;
}


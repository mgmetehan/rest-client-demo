package com.mgmetehan.restclientdemo.dto.response;

import com.mgmetehan.restclientdemo.model.Support;
import com.mgmetehan.restclientdemo.model.User;

public record UserDataResponse(
        User data,
        Support support
) {
}

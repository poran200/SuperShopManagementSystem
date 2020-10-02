package com.example.supershop.standard.services;

import com.example.supershop.dto.request.UserDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.User;

public interface UserService {
    Response create(UserDto userDto);

    Response fidByUserName(String userName);

    Response findByIdAndIsActive(long id);

    Response update(long id, User user);

    Response delete(long id);
}

package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.request.UserDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.Role;
import com.example.supershop.model.User;
import com.example.supershop.repository.RoleRepository;
import com.example.supershop.repository.UserRepository;
import com.example.supershop.standard.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.supershop.util.ResponseBuilder.getFailureResponse;
import static com.example.supershop.util.ResponseBuilder.getSuccessResponse;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response create(UserDto userDto) {
        var optionalUser = userRepository.findByUsername(userDto.getUsername());
        if (optionalUser.isPresent()) {
            return getFailureResponse(HttpStatus.BAD_REQUEST, "user already exist");
        } else {
            var user = modelMapper.map(userDto, User.class);
            var encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            user.addRole(new Role("ROLE_" + userDto.getRole()));
            var saveUser = userRepository.save(user);
            return getSuccessResponse(HttpStatus.CREATED, "User created ",
                    modelMapper.map(saveUser, UserDto.class));
        }
    }

    @Override
    public Response fidByUserName(String userName) {
        return null;
    }

    @Override
    public Response findByIdAndIsActive(long id) {
        return null;
    }

    @Override
    public Response update(long id, User user) {
        return null;
    }

    @Override
    public Response delete(long id) {
        return null;
    }
}

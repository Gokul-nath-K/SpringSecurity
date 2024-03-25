package FullMetal.SpringSecurity.Services;

import FullMetal.SpringSecurity.DTO.Response.UserResponse;
import FullMetal.SpringSecurity.Models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public  List<UserResponse> getAllUsers();

    public UserResponse getUserById(Long id);

    public UserResponse updateUser(Long id, User user);

    public boolean deleteUser(Long id);

}
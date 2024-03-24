package FullMetal.SpringSecurity.Services;

import FullMetal.SpringSecurity.Models.User;

import java.util.List;

public interface UserService {

    public List<User>  getAllUser();

    public User postUser(User user);

}
package FullMetal.SpringSecurity.Services.Implementations;

import FullMetal.SpringSecurity.Models.User;
import FullMetal.SpringSecurity.Repository.UserRepository;
import FullMetal.SpringSecurity.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository uRepo;

    @Override
    public List<User> getAllUser() {

        return uRepo.findAll();
    }

    @Override
    public User postUser(User user) {

        return uRepo.save(user);
    }
}

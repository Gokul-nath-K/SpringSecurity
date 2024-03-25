package FullMetal.SpringSecurity.Services.Implementations;

import FullMetal.SpringSecurity.DTO.Response.UserResponse;
import FullMetal.SpringSecurity.Models.User;
import FullMetal.SpringSecurity.Repository.UserRepository;
import FullMetal.SpringSecurity.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers() {

        List<User> user = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();

        for(User u : user) {

            userResponses.add( UserResponse
                    .builder()
                    .id(u.getId())
                    .name(u.getName())
                    .email(u.getEmail())
                    .phone(u.getPhone())
                    .role(u.getRole())
                    .build());
        }

        return userResponses;
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id).get();

        return UserResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    @Override
    public UserResponse updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            var userSaved = userRepository.save(user);

            return UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(userSaved.getEmail())
                    .phone(user.getPhone())
                    .role(user.getRole())
                    .build();

        } else {
            return null; // Or throw an exception indicating user not found
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false; // Or throw an exception indicating user not found
        }
    }
}

package blog.be.service;


import blog.be.dtos.UserResponseDto;
import blog.be.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    boolean existsUsersByEmail(String email);
    User findByEmail(String email);
    void registerUser();

    UserResponseDto findUserByUsername(String username);

}

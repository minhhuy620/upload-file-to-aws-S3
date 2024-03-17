package blog.be.service.impl;

import blog.be.common.ERole;
import blog.be.dtos.UserResponseDto;
import blog.be.entity.Role;
import blog.be.entity.User;
import blog.be.repository.UserRepository;
import blog.be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public boolean existsUsersByEmail(String email) {
        return userRepository.existsUsersByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void registerUser(){
        User user = new User();
        user.setCreated_at(new Date());
        user.setEmail("user@gmail.com");
        user.setUserName("user1");
        user.setPassword(passwordEncoder.encode("123456"));
//        Set<Role> role = new HashSet<>();
//        role.add(new Role(2,ERole.ROLE_USER));
//        user.setRoles(role);
        userRepository.save(user);
    }

    @Override
    public UserResponseDto findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Could not find this user");
        }
        return new UserResponseDto(user.getId(), user.getUserName(), user.getEmail(), user.getCreated_at());
    }
}

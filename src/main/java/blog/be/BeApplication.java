package blog.be;

import blog.be.entity.User;
import blog.be.repository.UserRepository;
//import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
@SpringBootApplication
//@EnableAdminServer
public class BeApplication{
    public static void main(String[] args) {
        SpringApplication.run(BeApplication.class, args);
    }
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Khi chương trình chạy
//        // Insert vào csdl một user.
//        User user = new User();
//        user.setCreated_at(new Date());
//        user.setUsername("admin");
//        user.setEmail("admin@gmail.com");
//        user.setPassword(passwordEncoder.encode("123456"));
//        userRepository.save(user);
//        System.out.println(user);
//    }
}

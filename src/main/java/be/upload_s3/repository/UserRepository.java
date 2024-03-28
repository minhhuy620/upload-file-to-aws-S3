package be.upload_s3.repository;

import be.upload_s3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
    boolean existsUsersByEmail(String email);
    User findByEmail(String email);

    @Query(value="select * from blog_test.user as u where u.username = :username",nativeQuery = true)
    User findUserByUsername(@Param("username") String username);

}

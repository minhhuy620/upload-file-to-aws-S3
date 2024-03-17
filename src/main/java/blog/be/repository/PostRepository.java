package blog.be.repository;

import blog.be.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.Date;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "select count(published) from post", nativeQuery = true)
    int countPostByPublished();

    Page<Post> findByTitleContaining(String title, Pageable pageable);
    @Query(value = "select * from blog_test.post as posts where (CAST(posts.created_at as date) between :startDate and :endDate)", nativeQuery = true)
    Page<Post> findAllPostsByTimeBetween(@Param("startDate")String startDate, @Param("endDate")String endDate, Pageable pageable);
    @Query(value = "select * from blog_test.post as posts inner join blog_test.user as user on posts.author = user.id where user.username = :username", nativeQuery = true)
    Page<Post> findAllPostsByUserName(@Param("username")String username, Pageable pageable);

    @Query(value = "select * from blog_test.post as posts inner join blog_test.user as user on posts.author = user.id where posts.author = :author", nativeQuery = true)
    Page<Post> findAllPostsByUserId(@Param("author")int userID, Pageable pageable);
}

package blog.be.repository;

import blog.be.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "select * from category as c where c.title = :name ", nativeQuery = true)
    Category findTitleByName(String name);
}

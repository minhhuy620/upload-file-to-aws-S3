package blog.be.dtos;

import blog.be.entity.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class PostDto {
    private String title;
    private String metaTitle;
    private String slug;
    private int published;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    private Date publishedAt;
    private String content;
    @JsonProperty("categories")
    private Set<String> categories = new HashSet<>();
}

package blog.be.dtos;

import blog.be.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class CommentDto {
    private int id;
    private Post post;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a")
    @Temporal(TemporalType.TIMESTAMP)
    private Date published;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    private String content;
}

package blog.be.entity;

import blog.be.common.ERole;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "role")
@Data
public class Role {

    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

//    public Role(int id, ERole name) {
//        this.id = id;
//        this.name = name;
//    }
}

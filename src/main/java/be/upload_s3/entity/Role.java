package be.upload_s3.entity;

import be.upload_s3.common.ERole;
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

    public Role() {
    }

    public Role(Integer id, ERole name) {
        this.id = id;
        this.name = name;
    }

}

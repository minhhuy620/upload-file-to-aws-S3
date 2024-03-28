package be.upload_s3.entity.token;

import be.upload_s3.entity.User;
import lombok.Data;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "token")
    private String token;

    @Column(name = "expire_date")
    private Instant expireDate;
}

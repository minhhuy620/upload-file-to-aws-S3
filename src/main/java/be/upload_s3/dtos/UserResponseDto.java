package be.upload_s3.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private int id;
    private String username;
    private String email;
    private Date create_at;
}

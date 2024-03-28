package be.upload_s3.dtos;


import lombok.Data;

import java.util.List;

@Data
public class AuthResponseDto {
    private String token;
    private String refreshToken;
    private List roles;
    private int userID;
    private String username;
    public AuthResponseDto(String token,String refreshToken, List roles, int userID, String username) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.username = username;
        this.roles = roles;
        this.userID = userID;
    }
}

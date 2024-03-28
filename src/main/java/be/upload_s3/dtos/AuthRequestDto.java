package be.upload_s3.dtos;


import lombok.Data;

import java.io.Serializable;

@Data
public class AuthRequestDto implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    private String username;
    private String password;
}

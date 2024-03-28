package be.upload_s3.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ApiResponse {
	private Boolean success;
    private String message;
    private Integer errorCode;
    private Object data;

}

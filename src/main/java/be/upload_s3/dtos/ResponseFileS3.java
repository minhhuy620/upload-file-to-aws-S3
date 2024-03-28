package be.upload_s3.dtos;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.Data;
import java.util.List;

@Data
public class ResponseFileS3 {
    List<S3ObjectSummary> filesFromS3;
    public ResponseFileS3(List<S3ObjectSummary> data) {
        this.filesFromS3 = data;
    }
}

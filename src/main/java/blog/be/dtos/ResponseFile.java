package blog.be.dtos;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class ResponseFile {
    private String name;
    private String url;
    private String type;
    private long size;
    private List<Object> keys;

    public ResponseFile(List<Object> keys) {
        this.keys = keys;
    }
    public ResponseFile(String name, String url, String type, long size) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }
}

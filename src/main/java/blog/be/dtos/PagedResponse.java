package blog.be.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class PagedResponse<T> {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private List<T> content;

    public PagedResponse(){}

    public PagedResponse(int page, int size, long totalElements, int totalPages, boolean last, List<T> content) {
        setContent(content);
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
        this.content = content;
    }

    public List<T> getContent() {
        return content == null ? null : new ArrayList<>(content);
    }

    public void setContent(List<T> content) {
        if (content == null) {
            this.content = null;
        } else {
            this.content = Collections.unmodifiableList(content);
        }
    }

    public boolean isLast() {
        return last;
    }
}

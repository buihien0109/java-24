package com.example.demothymeleaf;

import com.example.demothymeleaf.model.PageResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponseImpl<T> implements PageResponse<T> {
    List<T> data;
    int currentPage;
    int pageSize;

    @Override
    public List<T> getContent() {
        int startIndex = (currentPage - 1) * pageSize;
        if (startIndex < data.size()) {
            int endIndex = Math.min(startIndex + pageSize, data.size());
            return data.subList(startIndex, endIndex);
        }
        return List.of();
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getTotalPages() {
        return (int) Math.ceil((double) data.size() / pageSize);
    }

    @Override
    public int getTotalElements() {
        return data.size();
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }
}

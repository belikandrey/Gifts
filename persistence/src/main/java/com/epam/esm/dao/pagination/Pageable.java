package com.epam.esm.dao.pagination;

import java.util.Objects;

public class Pageable {
    private Integer size;
    private Integer page;

    public Pageable(Integer size, Integer page) {
        this.size = size;
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}

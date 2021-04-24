package com.epam.esm.dao.pagination;

import java.util.Objects;

public class Pageable {
    private int size;
    private int page;

    public Pageable(int size, int page) {
        this.size = size;
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pageable pageable = (Pageable) o;
        return size == pageable.size && page == pageable.page;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, page);
    }
}

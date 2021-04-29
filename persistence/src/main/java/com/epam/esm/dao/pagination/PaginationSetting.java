package com.epam.esm.dao.pagination;

public class PaginationSetting {
  private Integer size;
  private Integer page;

  public PaginationSetting(Integer size, Integer page) {
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

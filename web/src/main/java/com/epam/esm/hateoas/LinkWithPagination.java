package com.epam.esm.hateoas;

import org.springframework.hateoas.Link;

public class LinkWithPagination extends Link {

  private int size;
  private int page;

  public LinkWithPagination(Link link, int size, int page) {
    super(link.getHref(), link.getRel());
    this.page = page;
    this.size = size;
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
}

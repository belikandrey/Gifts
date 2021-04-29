package com.epam.esm.dao.pagination;

/** The type Pagination setting. */
public class PaginationSetting {
  /** The Size. */
  private Integer size;

  /** The Page. */
  private Integer page;

  /**
   * Instantiates a new Pagination setting.
   *
   * @param size the size
   * @param page the page
   */
  public PaginationSetting(Integer size, Integer page) {
    this.size = size;
    this.page = page;
  }

  /**
   * Gets size.
   *
   * @return the size
   */
  public Integer getSize() {
    return size;
  }

  /**
   * Sets size.
   *
   * @param size the size
   */
  public void setSize(Integer size) {
    this.size = size;
  }

  /**
   * Gets page.
   *
   * @return the page
   */
  public Integer getPage() {
    return page;
  }

  /**
   * Sets page.
   *
   * @param page the page
   */
  public void setPage(Integer page) {
    this.page = page;
  }
}

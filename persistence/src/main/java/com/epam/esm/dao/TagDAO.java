package com.epam.esm.dao;

import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.entity.Tag;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/** The interface Tag dao. */
public interface TagDAO {

  /**
   * Find by id optional.
   *
   * @param id the id
   * @return the {@link Optional} of {@link Tag}
   */
  Optional<Tag> findById(BigInteger id);

  /**
   * Find all list.
   *
   * @param paginationSetting the pagination setting {@link PaginationSetting}
   * @return the list of {@link Tag}
   */
  List<Tag> findAll(PaginationSetting paginationSetting);

  /**
   * Save tag.
   *
   * @param entity the {@link Tag}
   * @return the {@link Tag}
   */
  Tag save(Tag entity);

  /**
   * Delete by id.
   *
   * @param id the id
   */
  void deleteById(BigInteger id);

  /**
   * Find tag by name optional.
   *
   * @param name the name
   * @return the {@link Optional} of {@link Tag}
   */
  Optional<Tag> findTagByName(String name);

  /**
   * Count long.
   *
   * @return the long
   */
  Long count();

  /**
   * Find most popular tag.
   *
   * @return the {@link Optional} of {@link Tag}
   */
  Optional<Tag> findMostPopularTag();

  /**
   * Is tag used boolean.
   *
   * @param tagId the tag id
   * @return true if tag is used in certificates, otherwise false
   */
  boolean isTagUsed(BigInteger tagId);
}

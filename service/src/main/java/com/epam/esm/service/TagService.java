package com.epam.esm.service;

import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.TagDTO;

import java.math.BigInteger;
import java.util.Collection;

/**
 * Base interface for Tag service
 *
 * @see EntityService
 * @version 1.0
 * @author Andrey Belik
 */
public interface TagService extends EntityService<TagDTO, BigInteger> {
  /**
   * Find all tags method
   *
   * @return {@link Collection} of {@link TagDTO}
   */
  Collection<TagDTO> findAll(PaginationSetting paginationSetting);

  /**
   * Find tag by name method
   *
   * @param name name of tag
   * @return {@link TagDTO} from database
   */
  TagDTO findByName(String name);

  TagDTO add(TagDTO tagDTO);

  void delete(BigInteger id);
  /**
   * Check is tag is already exist
   *
   * <p>//@param tagDTO {@link TagDTO} for check
   *
   * @return true if tag exist, false in another way
   */
  Long count();

  TagDTO findMostPopularTag();

  boolean isTagUsed(BigInteger tagId);
}

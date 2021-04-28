package com.epam.esm.service;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ValidatorException;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;

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
  Collection<TagDTO> findAll(Pageable pageable);

  /**
   * Find all tags by certificate id method
   *
   * @param certificateId id of certificate
   * @return {@link Set} of {@link TagDTO}
   */
  Set<TagDTO> findTagsByCertificateId(BigInteger certificateId);

  /**
   * Find tag by name method
   *
   * @param name name of tag
   * @return {@link TagDTO} from database
   */
  TagDTO findByName(String name);

  void update(BigInteger id, TagDTO t) throws ValidatorException;
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

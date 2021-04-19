package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;

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
  Collection<TagDTO> findAll();

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

  /**
   * Check is tag is already exist
   *
   * @param tagDTO {@link TagDTO} for check
   * @return true if tag exist, false in another way
   */
  boolean isAlreadyExists(TagDTO tagDTO);
}

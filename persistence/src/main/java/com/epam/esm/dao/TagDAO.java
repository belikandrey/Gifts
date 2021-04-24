package com.epam.esm.dao;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.entity.Tag;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * Base Tag DAO interface
 *
 * @see com.epam.esm.dao.AbstractDAO
 * @version 1.0
 * @author Andrey Belik
 */
public interface TagDAO extends AbstractDAO<Tag, BigInteger> {
  /**
   * Find all tags method
   *
   * @return {@link Collection} of tags
   */
  Collection<Tag> findAll(Pageable pageable);

  /**
   * Find all tags by certificate id method
   *
   * @param certificateId id of certificate
   * @return {@link Set} of tags
   */
  Set<Tag> findTagsByCertificateId(BigInteger certificateId);

  /**
   * Check tag in DB method
   *
   * @param tag tag for check
   * @return true if exist, false in another way
   */
  boolean isAlreadyExist(Tag tag);

  /**
   * Find tag by name method
   *
   * @param name name of tag
   * @return {@link Optional} of tag
   */
  Optional<Tag> findTagByName(String name);

    Integer countTagsFromCertificateTag(BigInteger tagId);
}

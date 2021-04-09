package com.epam.esm.service.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.EntityService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Tag service
 *
 * @author Andrey Belik
 */
@Service
public class TagService implements EntityService<TagDTO, BigInteger> {

  private Validator<Tag> validator;
  private AbstractDAO<Tag, BigInteger> tagDao;
  private Converter<Tag, TagDTO> converter;

  /**
   * Constructor
   *
   * @param validator {@link com.epam.esm.validator.Validator}
   * @param tagDao {@link com.epam.esm.dao.AbstractDAO}
   * @param converter {@link com.epam.esm.dto.converter.Converter}
   */
  @Autowired
  public TagService(
      Validator<Tag> validator,
      AbstractDAO<Tag, BigInteger> tagDao,
      Converter<Tag, TagDTO> converter) {
    this.validator = validator;
    this.tagDao = tagDao;
    this.converter = converter;
  }

  /** Default constructor */
  public TagService() {}

  /**
   * Find all tags method
   *
   * @return {@link java.util.Collection} of tags
   */
  @Override
  public Collection<TagDTO> findAll() {
    return tagDao.findAll().stream().map(converter::convert).collect(Collectors.toSet());
  }

  /**
   * Find all tags by certificate id
   *
   * @param certificateId certificate id
   * @return {@link java.util.Collection} of tags
   */
  @Override
  public Collection<TagDTO> findAll(BigInteger certificateId) {
    return tagDao.findAll(certificateId).stream()
        .map(converter::convert)
        .collect(Collectors.toSet());
  }

  /**
   * Find by id method
   *
   * @param id id of tag
   * @return tag or null if tag not found
   */
  @Override
  public TagDTO find(BigInteger id) {
    final Tag tag = tagDao.find(id);
    return tag != null ? converter.convert(tag) : null;
  }

  /**
   * Add tag method
   *
   * @param tagDTO tag to add
   * @return added tag
   * @throws ValidatorException if tag is invalid
   */
  @Override
  public TagDTO add(TagDTO tagDTO) throws ValidatorException {
    Tag tag = converter.convert(tagDTO);
    validator.validate(tag);
    return tagDao.add(tag) > 0 ? tagDTO : null;
  }

  /**
   * Add tag for certificate method
   *
   * @param tagDTO {@link java.util.Set} of tags
   * @param certificateId id of certificate
   * @return {@link java.util.Set} of added tags
   * @throws ValidatorException if any tag is invalid
   */
  public Set<TagDTO> add(Set<TagDTO> tagDTO, BigInteger certificateId) throws ValidatorException {
    final Set<Tag> tags = tagDTO.stream().map(converter::convert).collect(Collectors.toSet());
    for (Tag tag : tags) {
      validator.validate(tag);
    }
    ((TagDAO) tagDao).add(tags, certificateId);
    return tagDTO;
  }

  /**
   * Update tag method
   *
   * @param id id of tag for update
   * @param tagDTO tag for update
   * @return count of updated rows
   * @throws ValidatorException if tag is invalid
   */
  @Override
  public int update(BigInteger id, TagDTO tagDTO) throws ValidatorException {
    Tag tag = converter.convert(tagDTO);
    validator.validate(tag);
    return tagDao.update(id, tag);
  }

  /**
   * Delete tag by id method
   *
   * @param id id of entity for delete
   * @return true if tag deleted, false in another way
   */
  @Override
  public boolean delete(BigInteger id) {
    return tagDao.delete(id) > 0;
  }
}

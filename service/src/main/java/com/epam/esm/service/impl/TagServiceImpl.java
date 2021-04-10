package com.epam.esm.service.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.EntityService;
import com.epam.esm.service.TagService;
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
public class TagServiceImpl implements TagService {

  private Validator<Tag> validator;
  private TagDAO tagDao;
  private Converter<Tag, TagDTO> converter;

  /**
   * Constructor
   *
   * @param validator {@link com.epam.esm.validator.Validator}
   * @param tagDao {@link com.epam.esm.dao.AbstractDAO}
   * @param converter {@link com.epam.esm.dto.converter.Converter}
   */
  @Autowired
  public TagServiceImpl(Validator<Tag> validator, TagDAO tagDao, Converter<Tag, TagDTO> converter) {
    this.validator = validator;
    this.tagDao = tagDao;
    this.converter = converter;
  }

  /** Default constructor */
  public TagServiceImpl() {}

  /**
   * Find all tags method
   *
   * @return {@link java.util.Collection} of tags
   */
  @Override
  public Collection<TagDTO> findAll() {
    return tagDao.findAll().stream().map(converter::convert).collect(Collectors.toSet());
  }

  @Override
  public Set<TagDTO> findTagsByCertificateId(BigInteger id) {
    return tagDao.findTagsByCertificateId(id).stream()
        .map(converter::convert)
        .collect(Collectors.toSet());
  }

  @Override
  public TagDTO findByName(String name) throws EntityNotFoundException {
    return converter.convert(tagDao.findTagByName(name));
  }

  /**
   * Find all tags by certificate id
   *
   * @param certificateId certificate id
   * @return {@link java.util.Collection} of tags
   */
  /* @Override
    public Collection<TagDTO> findAll(BigInteger certificateId) {
      return tagDao.findAll(certificateId).stream()
          .map(converter::convert)
          .collect(Collectors.toSet());
    }
  */
  /**
   * Find by id method
   *
   * @param id id of tag
   * @return tag or null if tag not found
   */
  @Override
  public TagDTO findById(BigInteger id) throws EntityNotFoundException {
    final Tag tag = tagDao.findById(id);
    return converter.convert(tag);
  }

  /**
   * Add tag method
   *
   * @param tagDTO tag to add
   * @return added tag
   * @throws ValidatorException if tag is invalid
   */
  @Override
  // TODO if tag exists in DB, return this tag
  public TagDTO add(TagDTO tagDTO) throws ValidatorException, EntityAlreadyExistException {
    Tag tag = converter.convert(tagDTO);
    validator.validate(tag);
    return converter.convert(tagDao.add(tag));
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
  public void update(BigInteger id, TagDTO tagDTO) throws EntityNotFoundException {
    tagDao.update(id, converter.convert(tagDTO));
  }

  /**
   * Delete tag by id method
   *
   * @param id id of entity for delete
   * @return true if tag deleted, false in another way
   */
  @Override
  public void delete(BigInteger id) throws EntityNotFoundException {
    tagDao.delete(id);
  }
}

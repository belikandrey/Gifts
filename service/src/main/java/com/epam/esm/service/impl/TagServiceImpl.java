package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;
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

  /**
   * Find all tags by certificate id method
   *
   * @param certificateId id of certificate
   * @return {@link Set} of {@link TagDTO}
   */
  @Override
  public Set<TagDTO> findTagsByCertificateId(BigInteger certificateId) {
    return tagDao.findTagsByCertificateId(certificateId).stream()
        .map(converter::convert)
        .collect(Collectors.toSet());
  }

  /**
   * Find tag by name method
   *
   * @param name name of tag
   * @return tag
   * @throws EntityNotFoundException if entity with this name not found
   */
  @Override
  public TagDTO findByName(String name) {
    final Optional<Tag> tag = tagDao.findTagByName(name);
    if (tag.isEmpty()) {
      throw new EntityNotFoundException("Tag with name : " + name + " not found");
    }
    return converter.convert(tag.get());
  }

  /**
   * Check that tag exist in database
   *
   * @param tagDTO {@link TagDTO} for check
   * @return true if tag exist, false in another way
   */
  @Override
  public boolean isAlreadyExists(TagDTO tagDTO) {
    final Tag tag = converter.convert(tagDTO);
    return tagDao.isAlreadyExist(tag);
  }

  /**
   * Find by id method
   *
   * @param id id of tag
   * @return tag
   * @throws EntityNotFoundException if entity with this id not found
   */
  @Override
  public TagDTO findById(BigInteger id) throws EntityNotFoundException {
    final Optional<Tag> tag = tagDao.findById(id);
    if (tag.isEmpty()) {
      throw new EntityNotFoundException("Tag with id : " + id + " not found");
    }
    return converter.convert(tag.get());
  }

  /**
   * Add tag method
   *
   * @param tagDTO tag to add
   * @return added tag
   * @throws ValidatorException if tag is invalid
   * @throws EntityAlreadyExistException if entity with this name already exist
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public TagDTO add(TagDTO tagDTO) throws ValidatorException {
    Tag tag = converter.convert(tagDTO);
    validator.validate(tag);
    final Tag addedTag = tagDao.add(tag);
    if (addedTag == null) {
      throw new EntityAlreadyExistException(
          "Tag with name : " + tagDTO.getName() + " already exist");
    }
    return converter.convert(addedTag);
  }

  /**
   * Update tag method
   *
   * @param id id of tag for update
   * @param tagDTO tag for update
   * @throws ValidatorException if tag is invalid
   */
  @Override
  public void update(BigInteger id, TagDTO tagDTO) throws ValidatorException {
    final Tag tag = converter.convert(tagDTO);
    validator.validate(tag);
    tagDao.update(id, tag);
  }

  /**
   * Delete tag by id method
   *
   * @param id id of entity for delete
   * @throws EntityNotFoundException if tag with this id not found
   */
  @Override
  public void delete(BigInteger id){
    if (!tagDao.delete(id)) {
      throw new EntityNotFoundException("Tag with id : " + id + " not found");
    }
  }
}
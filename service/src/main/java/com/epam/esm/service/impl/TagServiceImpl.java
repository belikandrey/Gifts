package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.EntityUsedException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Tag service
 *
 * @author Andrey Belik
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

  /** The Validator. */
  private Validator<Tag> validator;

  /** The Converter. */
  private Converter<Tag, TagDTO> converter;

  /** The Tag repository. */
  private TagDAO tagDAO;

  /**
   * Constructor
   *
   * @param validator {@link com.epam.esm.validator.Validator}
   * @param tagDAO the {@link TagDAO}
   * @param converter {@link com.epam.esm.dto.converter.Converter}
   */
  @Autowired
  public TagServiceImpl(Validator<Tag> validator, TagDAO tagDAO, Converter<Tag, TagDTO> converter) {
    this.validator = validator;
    this.tagDAO = tagDAO;
    this.converter = converter;
  }

  /** Default constructor */
  public TagServiceImpl() {}

  /**
   * Find all tags method
   *
   * @param paginationSetting the pagination setting
   * @return {@link java.util.Collection} of tags
   */
  @Override
  @Transactional(readOnly = true)
  public Collection<TagDTO> findAll(PaginationSetting paginationSetting) {
    return tagDAO.findAll(paginationSetting).stream()
        .map(converter::convertToDto)
        .collect(Collectors.toSet());
  }

  /**
   * Find tag by name method
   *
   * @param name name of tag
   * @return {@link TagDTO}
   * @exception EntityNotFoundException if entity with this name not found
   */
  @Override
  @Transactional(readOnly = true)
  public TagDTO findByName(String name) {
    final Optional<Tag> tag = tagDAO.findTagByName(name);
    if (tag.isEmpty()) {
      throw new EntityNotFoundException("Tag with name : " + name + " not found", Tag.class);
    }
    return converter.convertToDto(tag.get());
  }

  /**
   * Count long.
   *
   * @return the long
   */
  @Override
  public Long count() {
    return tagDAO.count();
  }

  /**
   * Find most popular tag.
   *
   * @return the {@link TagDTO}
   */
  @Override
  public TagDTO findMostPopularTag() {
    final Optional<Tag> mostPopularTag = tagDAO.findMostPopularTag();
    if (mostPopularTag.isEmpty()) {
      throw new EntityNotFoundException("Most popular tag not found", Tag.class);
    }
    return converter.convertToDto(mostPopularTag.get());
  }

  /**
   * Find by id method
   *
   * @param id id of tag
   * @return {@link TagDTO}
   * @exception EntityNotFoundException if entity with this id not found
   */
  @Override
  @Transactional(readOnly = true)
  public TagDTO findById(BigInteger id) {
    final Optional<Tag> tag = tagDAO.findById(id);
    if (tag.isEmpty()) {
      throw new EntityNotFoundException("Tag with id : " + id + " not found", Tag.class);
    }
    return converter.convertToDto(tag.get());
  }

  /**
   * Add tag method
   *
   * @param tagDTO tag to add
   * @return added {@link TagDTO}
   * @throws ValidatorException if tag is invalid
   * @exception EntityAlreadyExistException if entity with this name already exist
   */
  @Override
  public TagDTO add(TagDTO tagDTO) throws ValidatorException {
    Tag tag = converter.convertToEntity(tagDTO);
    validator.validate(tag);
    final Optional<Tag> tagByName = tagDAO.findTagByName(tagDTO.getName());
    if (tagByName.isPresent()) {
      throw new EntityAlreadyExistException(
          "Tag with name : " + tagDTO.getName() + " is already exist in db", Tag.class);
    }
    final Tag added = tagDAO.save(tag);
    return converter.convertToDto(added);
  }

  /**
   * Delete tag by id method
   *
   * @param id id of entity for delete
   * @exception EntityNotFoundException if tag with this id not found
   * @exception EntityUsedException if tag is used in certificates
   */
  @Override
  public void delete(BigInteger id) {
    if (tagDAO.findById(id).isEmpty()) {
      throw new EntityNotFoundException("Tag with id : " + id + " not found", Tag.class);
    }
    if (isTagUsed(id)) {
      throw new EntityUsedException(
          "Can not delete tag with id : " + id + ". Tag is used in certificates", Tag.class);
    }
    tagDAO.deleteById(id);
  }

  /**
   * Is tag used boolean.
   *
   * @param tagId the tag id
   * @return true if tag is used in certificates, otherwise false
   */
  @Override
  public boolean isTagUsed(BigInteger tagId) {
    return tagDAO.isTagUsed(tagId);
  }
}

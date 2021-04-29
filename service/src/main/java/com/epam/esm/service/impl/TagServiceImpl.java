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

  private Validator<Tag> validator;
  private Converter<Tag, TagDTO> converter;
  private TagDAO tagRepository;

  /**
   * Constructor
   *
   * @param validator {@link com.epam.esm.validator.Validator}
   * @param {@link com.epam.esm.dao.AbstractDAO}
   * @param converter {@link com.epam.esm.dto.converter.Converter}
   */
  @Autowired
  public TagServiceImpl(
      Validator<Tag> validator, TagDAO tagRepository, Converter<Tag, TagDTO> converter) {
    this.validator = validator;
    this.tagRepository = tagRepository;
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
  @Transactional(readOnly = true)
  public Collection<TagDTO> findAll(PaginationSetting paginationSetting) {
    return tagRepository.findAll(paginationSetting).stream()
        .map(converter::convertToDto)
        .collect(Collectors.toSet());
  }

  /**
   * Find tag by name method
   *
   * @param name name of tag
   * @return tag
   * @exception EntityNotFoundException if entity with this name not found
   */
  @Override
  @Transactional(readOnly = true)
  public TagDTO findByName(String name) {
    final Optional<Tag> tag = tagRepository.findTagByName(name);
    if (tag.isEmpty()) {
      throw new EntityNotFoundException("Tag with name : " + name + " not found", Tag.class);
    }
    return converter.convertToDto(tag.get());
  }

  @Override
  public Long count() {
    return tagRepository.count();
  }

  @Override
  public TagDTO findMostPopularTag() {
    final Optional<Tag> mostPopularTag = tagRepository.findMostPopularTag();
    if (mostPopularTag.isEmpty()) {
      throw new EntityNotFoundException("Most popular tag not found", Tag.class);
    }
    return converter.convertToDto(mostPopularTag.get());
  }

  /**
   * Check that tag exist in database
   *
   * @param tagDTO {@link TagDTO} for check
   * @return true if tag exist, false in another way
   */

  /**
   * Find by id method
   *
   * @param id id of tag
   * @return tag
   * @exception EntityNotFoundException if entity with this id not found
   */
  @Override
  @Transactional(readOnly = true)
  public TagDTO findById(BigInteger id) {
    final Optional<Tag> tag = tagRepository.findById(id);
    if (tag.isEmpty()) {
      throw new EntityNotFoundException("Tag with id : " + id + " not found", Tag.class);
    }
    return converter.convertToDto(tag.get());
  }

  /**
   * Add tag method
   *
   * @param tagDTO tag to add
   * @return added tag
   * @throws ValidatorException if tag is invalid
   * @exception EntityAlreadyExistException if entity with this name already exist
   */
  @Override
  public TagDTO add(TagDTO tagDTO) throws ValidatorException {
    Tag tag = converter.convertToEntity(tagDTO);
    validator.validate(tag);
    final Optional<Tag> tagByName = tagRepository.findTagByName(tagDTO.getName());
    if (tagByName.isPresent()) {
      throw new EntityAlreadyExistException(
          "Tag with name : " + tagDTO.getName() + " is already exist in db", Tag.class);
    }
    final Tag added = tagRepository.save(tag);
    return converter.convertToDto(added);
  }

  /**
   * Update tag method
   *
   * @param id id of tag for update
   * @param tagDTO tag for update
   * @throws ValidatorException if tag is invalid
   */

  /**
   * Delete tag by id method
   *
   * @param id id of entity for delete
   * @exception EntityNotFoundException if tag with this id not found
   * @exception EntityUsedException if tag is used in certificates
   */
  @Override
  public void delete(BigInteger id) {
    if (tagRepository.findById(id).isEmpty()) {
      throw new EntityNotFoundException("Tag with id : " + id + " not found", Tag.class);
    }
    if (isTagUsed(id)) {
      throw new EntityUsedException(
          "Can not delete tag with id : " + id + ". Tag is used in certificates", Tag.class);
    }
    System.out.println("After is tag used");
    tagRepository.deleteById(id);
  }

  @Override
  public boolean isTagUsed(BigInteger tagId) {
    return tagRepository.isTagUsed(tagId);
  }
}

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
import com.epam.esm.messages.Translator;
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

  private Translator translator;

  private static final String TAG_WITH_ID_KEY = "tag.tag_with_id";
  private static final String NOT_FOUND_KEY = "service.not_found";
  private static final String TAG_WITH_NAME_KEY = "tag.tag_with_name";
  private static final String TAG_MOST_POPULAR_KEY = "tag.most_popular_not_found";
  private static final String TAG_ALREADY_EXISTS_KEY = "tag.tag_is_already_exist";
  private static final String TAG_CANNOT_DELETE_KEY = "tag.cannot_delete";
  private static final String TAG_IS_USED_KEY = "tag.tag_is_used";

  /**
   * Constructor
   *
   * @param validator {@link Validator}
   * @param tagDAO the {@link TagDAO}
   * @param converter {@link Converter}
   * @param translator
   */
  @Autowired
  public TagServiceImpl(
      Validator<Tag> validator,
      TagDAO tagDAO,
      Converter<Tag, TagDTO> converter,
      Translator translator) {
    this.validator = validator;
    this.tagDAO = tagDAO;
    this.converter = converter;
    this.translator = translator;
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
    Tag tag =
        tagDAO
            .findTagByName(name)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        translator.toLocale(TAG_WITH_NAME_KEY)
                            + " : "
                            + name
                            + " "
                            + translator.toLocale(NOT_FOUND_KEY),
                        Tag.class));
    return converter.convertToDto(tag);
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
    Tag tag =
        tagDAO
            .findMostPopularTag()
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        translator.toLocale(TAG_MOST_POPULAR_KEY), Tag.class));
    return converter.convertToDto(tag);
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
    Tag tag =
        tagDAO
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        translator.toLocale(TAG_WITH_ID_KEY)
                            + " : "
                            + id
                            + " "
                            + translator.toLocale(NOT_FOUND_KEY),
                        Tag.class));
    return converter.convertToDto(tag);
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
          translator.toLocale(TAG_WITH_NAME_KEY)
              + " : "
              + tagDTO.getName()
              + " "
              + translator.toLocale(TAG_ALREADY_EXISTS_KEY),
          Tag.class);
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
      throw new EntityNotFoundException(
          translator.toLocale(TAG_WITH_ID_KEY)
              + " : "
              + id
              + " "
              + translator.toLocale(NOT_FOUND_KEY),
          Tag.class);
    }
    if (isTagUsed(id)) {
      throw new EntityUsedException(
          translator.toLocale(TAG_CANNOT_DELETE_KEY)
              + " : "
              + id
              + ". "
              + translator.toLocale(TAG_IS_USED_KEY),
          Tag.class);
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

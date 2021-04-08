package com.epam.esm.service.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.impl.TagConverter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.validator.impl.TagValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class TagServiceTest {
  @Autowired private TagService tagService;
  @Autowired private TagDAO tagDAO;
  @Autowired private TagConverter converter;
  @Autowired private TagValidator validator;

  private static final String FIRST_NAME = "#cool";
  private static final BigInteger FIRST_ID = BigInteger.ONE;
  private static final String SECOND_NAME = "#beautiful";
  private static final BigInteger SECOND_ID = BigInteger.TWO;
  private static final String WRONG_NAME = "A";
  private static final BigInteger CERTIFICATE_ID = BigInteger.TEN;
  private static final int DAO_UPDATE_RETURN_VALUE = 3;
  private static final int DAO_UPDATE_BAD_RETURN_VALUE = 0;

  // Mock of TagDAO returns tags list. Expected collection of tagDTO with same id/name
  @Test
  public void findAllTest() {
    final List<Tag> tags = List.of(new Tag(FIRST_ID, FIRST_NAME), new Tag(SECOND_ID, SECOND_NAME));
    when(tagDAO.findAll()).thenReturn(tags);
    when(converter.convert(any(Tag.class))).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
    final Collection<TagDTO> tagsDTO = tagService.findAll();
    assertNotNull(tagsDTO);
    assertFalse(tagsDTO.isEmpty());
    final long count =
        tagsDTO.stream()
            .filter((p) -> p.getName().equals(FIRST_NAME) || p.getName().equals(SECOND_NAME))
            .count();
    assertEquals(count, 2);
  }

  // Given certificate ID. Mock of TagDAO returns tags list for this certificate. Expected tagDTO
  // collection
  // with same id/name
  @Test
  public void findAllByCertificateIdTest() {
    final List<Tag> tags = List.of(new Tag(FIRST_NAME), new Tag(SECOND_NAME));
    when(tagDAO.findAll(CERTIFICATE_ID)).thenReturn(tags);
    when(converter.convert(any(Tag.class))).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
    final Collection<TagDTO> tagsDTO = tagService.findAll(CERTIFICATE_ID);
    assertNotNull(tagsDTO);
    assertFalse(tagsDTO.isEmpty());
    final long count =
        tagsDTO.stream()
            .filter((p) -> p.getName().equals(FIRST_NAME) || p.getName().equals(SECOND_NAME))
            .count();
    assertEquals(count, 2);
  }

  // Given tagID. Mock of tagDAO returns tags list with tagID. Expected tagDTO collection with same
  // id/name
  @Test
  public void findTest() {
    final Tag tag = new Tag(FIRST_ID, FIRST_NAME);
    when(tagDAO.find(FIRST_ID)).thenReturn(tag);
    when(converter.convert(tag)).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
    final TagDTO tagDTO = tagService.find(FIRST_ID);
    assertNotNull(tagDTO);
    assertEquals(tagDTO.getName(), tag.getName());
  }

  // Mock of tagDAO returns null. Expected null
  @Test
  public void nullFindTest() {
    when(tagDAO.find(any(BigInteger.class))).thenReturn(null);
    when(converter.convert(any(Tag.class))).thenReturn(null);
    final TagDTO tagDTO = tagService.find(FIRST_ID);
    assertNull(tagDTO);
  }

  // Given tag. Mock of tagDAO returns positive update value. Expected tagDTO with same id/name
  // without exceptions
  @Test
  public void addTest() throws ValidatorException {
    when(tagDAO.add(any(Tag.class))).thenReturn(DAO_UPDATE_RETURN_VALUE);
    when(converter.convert(any(TagDTO.class))).thenReturn(new Tag(FIRST_ID, FIRST_NAME));
    doNothing().when(validator).validate(any(Tag.class));
    final TagDTO tagDTO =
        assertDoesNotThrow(() -> tagService.add(new TagDTO(FIRST_ID, FIRST_NAME)));
    assertNotNull(tagDTO);
  }

  // Given tag. Mock of tagDAO returns negative update value. Expected work without exceptions
  @Test
  public void addNotAddedTag() throws ValidatorException {
    final Tag tag = new Tag(FIRST_ID, FIRST_NAME);
    when(tagDAO.add(tag)).thenReturn(DAO_UPDATE_BAD_RETURN_VALUE);
    when(converter.convert(any(TagDTO.class))).thenReturn(new Tag(FIRST_ID, FIRST_NAME));
    doNothing().when(validator).validate(any(Tag.class));
    assertDoesNotThrow(() -> tagService.add(new TagDTO(FIRST_ID, FIRST_NAME)));
  }

  // Given invalid tag. Expected ValidatorException
  @Test
  public void addTagWithBadName() throws ValidatorException {
    doThrow(ValidatorException.class).when(validator).validate(any(Tag.class));
    when(converter.convert(any(Tag.class))).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
    assertThrows(ValidatorException.class, () -> tagService.add(new TagDTO(FIRST_ID, WRONG_NAME)));
  }

  // Given certificate Id and tagDTO for update. Mock of tagDAO return positive value. Expected work
  // without exceptions
  @Test
  public void updateTag() {
    when(tagDAO.update(any(), any(Tag.class))).thenReturn(DAO_UPDATE_RETURN_VALUE);
    when(converter.convert(any(TagDTO.class))).thenReturn(new Tag(FIRST_ID, FIRST_NAME));
    assertDoesNotThrow(() -> tagService.update(FIRST_ID, new TagDTO(FIRST_ID, FIRST_NAME)));
  }

  // Given tag ID. Expected true.
  @Test
  public void deleteTest() {
    when(tagDAO.delete(any())).thenReturn(DAO_UPDATE_RETURN_VALUE);
    assertTrue(tagService.delete(FIRST_ID));
  }
}

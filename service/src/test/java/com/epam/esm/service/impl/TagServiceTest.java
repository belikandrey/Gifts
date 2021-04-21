package com.epam.esm.service.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.impl.TagDAOImpl;
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
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class TagServiceTest {
  /*
  @Autowired private TagServiceImpl tagService;
  @Autowired private TagDAOImpl tagDAO;
  @Autowired private TagConverter converter;
  @Autowired private TagValidator validator;

  private static final String FIRST_NAME = "#cool";
  private static final BigInteger FIRST_ID = BigInteger.ONE;
  private static final String SECOND_NAME = "#beautiful";
  private static final BigInteger SECOND_ID = BigInteger.TWO;
  private static final String WRONG_NAME = "A";
  private static final BigInteger CERTIFICATE_ID = BigInteger.TEN;

  // Mock of TagDAO returns tags list. Expected collection of tagDTO
  @Test
  public void findAllTest() {
    final Tag firstTag = new Tag(FIRST_ID, FIRST_NAME);
    final Tag secondTag = new Tag(SECOND_ID, SECOND_NAME);
    final Set<Tag> tags = Set.of(firstTag, secondTag);
    when(tagDAO.findAll()).thenReturn(tags);
    when(converter.convert(firstTag)).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
    when(converter.convert(secondTag)).thenReturn(new TagDTO(SECOND_ID, SECOND_NAME));
    final Collection<TagDTO> tagsDTO = tagService.findAll();
    assertNotNull(tagsDTO);
    assertFalse(tagsDTO.isEmpty());
    final long count =
        tagsDTO.stream()
            .filter(
                (p) ->
                    p.getName().equals(firstTag.getName())
                        || p.getName().equals(secondTag.getName()))
            .count();
    assertEquals(2, count);
  }

  // Given certificate ID. Mock of TagDAO returns tags list for this certificate. Expected tagDTO
  // collection
  // with same id/name
  @Test
  public void findAllByCertificateIdTest() {
    final Tag firstTag = new Tag(FIRST_NAME);
    final Tag secondTag = new Tag(SECOND_NAME);
    final Set<Tag> tags = Set.of(firstTag, secondTag);
    when(tagDAO.findTagsByCertificateId(CERTIFICATE_ID)).thenReturn(tags);
    when(converter.convert(firstTag)).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
    when(converter.convert(secondTag)).thenReturn(new TagDTO(SECOND_ID, SECOND_NAME));
    final Collection<TagDTO> tagsDTO = tagService.findTagsByCertificateId(CERTIFICATE_ID);
    assertNotNull(tagsDTO);
    assertFalse(tagsDTO.isEmpty());
    final long count =
        tagsDTO.stream()
            .filter((p) -> p.getName().equals(FIRST_NAME) || p.getName().equals(SECOND_NAME))
            .count();
    assertEquals(2, count);
  }

  // Given tag name. Mock of tagDAO returns tag with equals tag name. Expected tagDTO with same
  // name
  @Test
  public void findByNameTest() {
    final Tag tag = new Tag(FIRST_ID, FIRST_NAME);
    when(tagDAO.findTagByName(FIRST_NAME)).thenReturn(Optional.of(tag));
    when(converter.convert(tag)).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
    final TagDTO tagByName = tagService.findByName(FIRST_NAME);
    assertNotNull(tagByName);
    assertEquals(tagByName.getName(), FIRST_NAME);
  }

  // Given tagID. Mock of tagDAO returns tag with same ID. Expected tagDTO with same
  // id
  @Test
  public void findByIdTest() {
    final Tag tag = new Tag(FIRST_ID, FIRST_NAME);
    when(tagDAO.findById(FIRST_ID)).thenReturn(Optional.of(tag));
    when(converter.convert(tag)).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
    final TagDTO tagDTO = tagService.findById(FIRST_ID);
    assertNotNull(tagDTO);
    assertEquals(tagDTO.getName(), tag.getName());
  }

  // Given tag. Mock of tagDAO returns added tag. Expected tagDTO with same id/name
  // without exceptions
  @Test
  public void addTest() throws ValidatorException {
    when(tagDAO.add(any(Tag.class))).thenReturn(new Tag(FIRST_ID, FIRST_NAME));
    when(converter.convert(any(TagDTO.class))).thenReturn(new Tag(FIRST_ID, FIRST_NAME));
    when(converter.convert(any(Tag.class))).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
    doNothing().when(validator).validate(any(Tag.class));
    final TagDTO tagDTO =
        assertDoesNotThrow(() -> tagService.add(new TagDTO(FIRST_ID, FIRST_NAME)));
    assertNotNull(tagDTO);
  }

  // Given invalid tag. Expected ValidatorException
  @Test
  public void addTagWithBadNameTest() throws ValidatorException {
    doThrow(ValidatorException.class).when(validator).validate(any(Tag.class));
    when(converter.convert(any(Tag.class))).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
    assertThrows(ValidatorException.class, () -> tagService.add(new TagDTO(FIRST_ID, WRONG_NAME)));
  }

  // Given certificate Id and tagDTO for update. Mock of tagDAO return positive value. Expected work
  // without exceptions
  @Test
  public void updateTag() {
    when(tagDAO.update(any(), any(Tag.class))).thenReturn(true);
    when(converter.convert(any(TagDTO.class))).thenReturn(new Tag(FIRST_ID, FIRST_NAME));
    assertDoesNotThrow(() -> tagService.update(FIRST_ID, new TagDTO(FIRST_ID, FIRST_NAME)));
  }

  // Given tag ID. Expected true.
  @Test
  public void deleteTest() {
    when(tagDAO.delete(any())).thenReturn(true);
    assertDoesNotThrow(() -> tagService.delete(FIRST_ID));
  }*/
}

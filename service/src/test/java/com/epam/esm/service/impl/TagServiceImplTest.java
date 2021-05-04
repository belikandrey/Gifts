package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.EntityUsedException;
import com.epam.esm.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class TagServiceImplTest {
  @Mock Validator<Tag> validator;
  @Mock Converter<Tag, TagDTO> converter;
  @Mock TagDAO tagDAO;
  @InjectMocks TagServiceImpl tagServiceImpl;

  private static final String FIRST_NAME = "#cool";
  private static final BigInteger FIRST_ID = BigInteger.ONE;
  private static final String SECOND_NAME = "#beautiful";
  private static final BigInteger SECOND_ID = BigInteger.TWO;
  private static final String WRONG_NAME = "A";
  private static final BigInteger CERTIFICATE_ID = BigInteger.TEN;
  private static final Tag TAG = new Tag(FIRST_ID, FIRST_NAME);
  private static final PaginationSetting PAGINATION_SETTING = PaginationSetting.getInstance(10, 15);
  private static final TagDTO TAG_DTO = new TagDTO(FIRST_ID, FIRST_NAME);

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAll() {
    when(tagDAO.findAll(PAGINATION_SETTING)).thenReturn(List.of(TAG));
    when(converter.convertToDto(TAG)).thenReturn(TAG_DTO);

    Set<TagDTO> result = (Set<TagDTO>) tagServiceImpl.findAll(PAGINATION_SETTING);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.contains(TAG_DTO));
  }

  @Test
  void testFindByName() {
    when(converter.convertToDto(TAG)).thenReturn(TAG_DTO);
    when(tagDAO.findTagByName(FIRST_NAME)).thenReturn(Optional.of(TAG));

    TagDTO result = tagServiceImpl.findByName(FIRST_NAME);
    assertNotNull(result);
    assertEquals(TAG.getId(), result.getId());
    assertEquals(TAG.getName(), result.getName());
  }

  @Test
  void testCount() {
    when(tagDAO.count()).thenReturn(Long.valueOf(1));

    Long result = tagServiceImpl.count();
    assertEquals(Long.valueOf(1), result);
  }

  @Test
  void testFindMostPopularTag() {
    when(converter.convertToDto(TAG)).thenReturn(TAG_DTO);
    when(tagDAO.findMostPopularTag()).thenReturn(Optional.of(TAG));

    TagDTO result = tagServiceImpl.findMostPopularTag();
    assertNotNull(result);
    assertEquals(TAG.getId(), result.getId());
    assertEquals(TAG.getName(), result.getName());
  }

  @Test
  void testFindById() {
    when(converter.convertToDto(TAG)).thenReturn(TAG_DTO);
    when(tagDAO.findById(FIRST_ID)).thenReturn(Optional.of(TAG));

    TagDTO result = tagServiceImpl.findById(FIRST_ID);
    assertNotNull(result);
    assertEquals(TAG.getId(), result.getId());
    assertEquals(TAG.getName(), result.getName());
  }

  @Test
  void testAdd() {
    when(converter.convertToEntity(TAG_DTO)).thenReturn(TAG);
    when(converter.convertToDto(TAG)).thenReturn(TAG_DTO);
    when(tagDAO.save(TAG)).thenReturn(TAG);
    when(tagDAO.findTagByName(FIRST_NAME)).thenReturn(Optional.of(TAG));

    assertThrows(EntityAlreadyExistException.class, ()->tagServiceImpl.add(TAG_DTO));
    when(tagDAO.findTagByName(FIRST_NAME)).thenReturn(Optional.empty());
    TagDTO result = tagServiceImpl.add(TAG_DTO);
    assertNotNull(result);
    assertEquals(TAG_DTO.getId(), result.getId());
    assertEquals(TAG_DTO.getName(), result.getName());

  }

  @Test
  void testDelete() {
    when(tagDAO.findById(FIRST_ID)).thenReturn(Optional.of(TAG));
    when(tagDAO.isTagUsed(FIRST_ID)).thenReturn(true);

    assertThrows(EntityUsedException.class, ()->tagServiceImpl.delete(FIRST_ID));

    when(tagDAO.findById(FIRST_ID)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, ()->tagServiceImpl.delete(FIRST_ID));
  }

  @Test
  void testIsTagUsed() {
    when(tagDAO.isTagUsed(FIRST_ID)).thenReturn(true);

    boolean result = tagServiceImpl.isTagUsed(FIRST_ID);
    assertTrue(result);

    when(tagDAO.isTagUsed(FIRST_ID)).thenReturn(false);

    boolean secondResult = tagServiceImpl.isTagUsed(FIRST_ID);

    assertFalse(secondResult);
  }
}

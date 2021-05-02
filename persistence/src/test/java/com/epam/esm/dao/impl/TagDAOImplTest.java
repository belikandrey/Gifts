package com.epam.esm.dao.impl;

import com.epam.esm.config.DaoTestConfig;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = DaoTestConfig.class)
@Transactional
class TagDAOImplTest {
  private TagDAOImpl tagDAO;
  private static final String FIRST_TAG_NAME = "#cool";
  private static final String MOST_POPULAR_TAG_NAME = "#cool";
  private static final BigInteger MOST_POPULAR_TAG_ID = BigInteger.ONE;
  private static final BigInteger NOT_USED_TAG_ID = BigInteger.valueOf(12);
  private static final Tag NEW_TAG = new Tag("#newTag");

  @Autowired
  public TagDAOImplTest(TagDAOImpl tagDAO) {
    this.tagDAO = tagDAO;
  }

  @Test
  public void findAllTest() {
    final List<Tag> all = tagDAO.findAll(new PaginationSetting(10, 1));
    assertNotNull(all);
    assertFalse(all.isEmpty());
    assertEquals(10, all.size());
  }

  @Test
  public void findByIdTest() {
    Optional<Tag> tagById = tagDAO.findById(BigInteger.ONE);
    assertTrue(tagById.isPresent());
    assertEquals(FIRST_TAG_NAME, tagById.get().getName());
    tagById = tagDAO.findById(BigInteger.valueOf(20));
    assertTrue(tagById.isEmpty());
  }

  @Test
  public void countTest() {
    final Long count = tagDAO.count();
    assertNotNull(count);
    assertEquals(count, 12);
  }

  @Test
  public void findByNameTest() {
    final Optional<Tag> tagByName = tagDAO.findTagByName(FIRST_TAG_NAME);
    assertTrue(tagByName.isPresent());
    assertEquals(FIRST_TAG_NAME, tagByName.get().getName());
  }

  @Test
  public void findMostUsedTag() {
    final Optional<Tag> mostPopularTagOptional = tagDAO.findMostPopularTag();
    assertTrue(mostPopularTagOptional.isPresent());
    final Tag mostPopularTag = mostPopularTagOptional.get();
    assertEquals(MOST_POPULAR_TAG_NAME, mostPopularTag.getName());
    assertEquals(MOST_POPULAR_TAG_ID, mostPopularTag.getId());
  }

  @Test
  public void isTagUsedTest() {
    boolean isTagUsed = tagDAO.isTagUsed(MOST_POPULAR_TAG_ID);
    assertTrue(isTagUsed);
    isTagUsed = tagDAO.isTagUsed(NOT_USED_TAG_ID);
    assertFalse(isTagUsed);
  }

  @Test
  public void saveTest() {
    tagDAO.save(NEW_TAG);
    Optional<Tag> tagByName = tagDAO.findTagByName(NEW_TAG.getName());
    assertTrue(tagByName.isPresent());
  }

  @Test
  public void deleteByIdTest() {
    assertDoesNotThrow(()->tagDAO.deleteById(MOST_POPULAR_TAG_ID));
    final Optional<Tag> tagById = tagDAO.findById(MOST_POPULAR_TAG_ID);
    assertTrue(tagById.isEmpty());
  }
}

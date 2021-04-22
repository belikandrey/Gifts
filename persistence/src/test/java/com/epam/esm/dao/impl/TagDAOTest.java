package com.epam.esm.dao.impl;

import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagDAOTest {
//
//  private static JdbcTemplate jdbcTemplate;
//  private static TagDAOImpl tagDAO;
//  private static EmbeddedDatabase embeddedDatabase;
//  private static RowMapper<Tag> rowMapper = new BeanPropertyRowMapper<>(Tag.class);
//  private Tag tag = new Tag("#noCool");
//  private static final String TAG_NAME = "#cool";
//  private static final BigInteger CERTIFICATE_ID = BigInteger.TWO;
//  private static final BigInteger TAG_ID = BigInteger.ONE;
//  private static final String NOT_ADDED_TAG_NAME = "NewTagName";
//  private static final BigInteger SECOND_TAG_ID = BigInteger.TEN;
//  private static final BigInteger ADDED_TAG_ID = BigInteger.valueOf(12);
//
//  public static DataSource dataSource() {
//    embeddedDatabase =
//        new EmbeddedDatabaseBuilder()
//            .setType(EmbeddedDatabaseType.H2)
//            .addScript("sql/data-h2.sql")
//            .build();
//    return embeddedDatabase;
//  }
///*
//  @BeforeAll
//  public static void init() {
//    jdbcTemplate = new JdbcTemplate(dataSource());
//    tagDAO = new TagDAOImpl(jdbcTemplate, rowMapper);
//  }
//*/
//  @Test
//  void findByIdTest() {
//    final Tag tag = tagDAO.findById(TAG_ID).orElse(null);
//    assertNotNull(tag);
//    assertEquals(tag.getId(), TAG_ID);
//    assertEquals(tag.getName(), TAG_NAME);
//  }
//
//  @Test
//  void addTest() {
//    final Tag addedTag = tagDAO.add(tag);
//    assertEquals(addedTag.getName(), tag.getName());
//    assertEquals(addedTag.getId(), ADDED_TAG_ID);
//  }
//
//  @Test
//  void updateTest() {
//    assertThrows(UnsupportedOperationException.class, () -> tagDAO.update(null, null));
//  }
//
//  @Test
//  void deleteTest() {
//    assertTrue(tagDAO.delete(BigInteger.valueOf(8)));
//  }
//
//  @Test
//  void isAlreadyExistTest() {
//    assertTrue(tagDAO.isAlreadyExist(tag));
//    assertFalse(tagDAO.isAlreadyExist(new Tag(NOT_ADDED_TAG_NAME)));
//  }
//
//  @Test
//  void findByNameTest() {
//    final Tag tag = tagDAO.findTagByName(TAG_NAME).orElse(null);
//    assertNotNull(tag);
//    assertEquals(tag.getId(), TAG_ID);
//    assertEquals(tag.getName(), TAG_NAME);
//  }
//
//  @Test
//  void findAllTest() {
//    final Collection<Tag> tags = tagDAO.findAll();
//    assertNotNull(tags);
//    assertEquals(12, tags.size());
//    assertTrue(tags.stream().anyMatch((p) -> p.getName().equals(TAG_NAME)));
//  }
//
//  @Test
//  void findAllByCertificateIdTest() {
//    final Collection<Tag> tags = tagDAO.findTagsByCertificateId(CERTIFICATE_ID);
//    assertNotNull(tags);
//    assertEquals(tags.size(), 3);
//    assertTrue(tags.stream().anyMatch((p) -> p.getName().equals(TAG_NAME)));
//  }
//
//  @AfterAll
//  static void clean() {
//    embeddedDatabase.shutdown();
//  }
}

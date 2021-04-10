package com.epam.esm.dao.impl;

import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TagDAOTest {
/*
  private static JdbcTemplate jdbcTemplate;
  private static TagDAOImpl tagDAO;
  private static EmbeddedDatabase embeddedDatabase;
  private Tag tag = new Tag("#noCool");
  private static final String TAG_NAME = "#cool";
  private static final BigInteger CERTIFICATE_ID = BigInteger.TWO;
  private static final BigInteger TAG_ID = BigInteger.ONE;
  private static final BigInteger SECOND_TAG_ID = BigInteger.TEN;

  public static DataSource dataSource() {
    embeddedDatabase =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("sql/init_h2.sql")
            .build();
    return embeddedDatabase;
  }

  @BeforeAll
  public static void init() {
    jdbcTemplate = new JdbcTemplate(dataSource());
    tagDAO = new TagDAOImpl(jdbcTemplate);
  }

  @Test
  void findAllByCertificateIdTest() {
    final Collection<Tag> tags = tagDAO.findAll();
    assertNotNull(tags);
    assertEquals(tags.size(), 11);
    assertTrue(tags.stream().anyMatch((p) -> p.getName().equals(TAG_NAME)));
  }

  @Test
  void testFindAllTest() {
    final Collection<Tag> tags = tagDAO.findAll(CERTIFICATE_ID);
    assertNotNull(tags);
    assertEquals(tags.size(), 3);
    assertTrue(tags.stream().anyMatch((p) -> p.getName().equals(TAG_NAME)));
  }

  @Test
  void findByIdTest() {
    final Tag tag = tagDAO.find(TAG_ID);
    assertNotNull(tag);
    assertEquals(tag.getId(), TAG_ID);
    assertEquals(tag.getName(), TAG_NAME);
  }

  @Test
  void findByNameTest() {
    final Tag tag = tagDAO.find(TAG_NAME);
    assertNotNull(tag);
    assertEquals(tag.getId(), TAG_ID);
    assertEquals(tag.getName(), TAG_NAME);
  }

  @Test
  void addTest() {
    assertTrue(tagDAO.add(tag) > 0);
    assertTrue(tagDAO.findAll().stream().anyMatch((p) -> p.getName().equals(tag.getName())));
  }

  @Test
  void update() {
    assertThrows(UnsupportedOperationException.class, () -> tagDAO.update(null, null));
  }

  @Test
  void delete() {
    assertTrue(tagDAO.delete(BigInteger.valueOf(8)) > 0);
  }

  @AfterAll
  static void clean() {
    embeddedDatabase.shutdown();
  }*/
}

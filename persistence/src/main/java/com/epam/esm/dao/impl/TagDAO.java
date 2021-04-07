package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Class that interacts with the database
 *
 * @author Andrey Belik
 * @version 1.0
 */
@Component
public class TagDAO implements AbstractDAO<Tag, BigInteger> {
    private final JdbcTemplate jdbcTemplate;

    private final static String SQL_FIND_ALL = "SELECT id, name FROM gifts.tag";
    private final static String SQL_FIND_ALL_BY_CERTIFICATE_ID = SQL_FIND_ALL +
            " JOIN gifts.certificate_tag ON tag.id = certificate_tag.tag_id" +
            " WHERE certificate_id=?";
    private final static String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE id = ?";
    private final static String SQL_ADD = "INSERT INTO gifts.tag(name) VALUES(?)";
    private final static String SQL_DELETE = "DELETE FROM gifts.tag WHERE id=?";
    private final static String SQL_FIND_BY_NAME = "SELECT id, name FROM gifts.tag WHERE name=?";
    private final static String SQL_ADD_TAG_CERTIFICATE = "INSERT INTO gifts.certificate_tag(certificate_id, tag_id) " +
            "VALUES(?, ?)";


    /**
     * Constructor
     *
     * @param jdbcTemplate {@link org.springframework.jdbc.core.JdbcTemplate}
     */
    @Autowired
    public TagDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Find all tags method method
     *
     * @return {@link java.util.Collection} of tags
     */
    @Override
    public Collection<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper<>(Tag.class));
    }

    /**
     * Find all tags by certificate id method
     *
     * @param certificateId id of the certificate for find
     * @return {@link java.util.Collection} of tags
     */
    @Override
    public Collection<Tag> findAll(BigInteger certificateId) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_CERTIFICATE_ID, new BeanPropertyRowMapper<>(Tag.class), certificateId.longValue());
    }

    /**
     * Find tag by id method
     *
     * @param id id of the tag
     * @return tag or null if tag not found
     */
    @Override
    public Tag find(BigInteger id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id.longValue())
                .stream().
                        findAny().
                        orElse(null);
    }

    /**
     * Find tag by name method
     *
     * @param name name of tag
     * @return tag or null if tag not found
     */
    public Tag find(String name) {
        return jdbcTemplate.query(SQL_FIND_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), name)
                .stream().
                        findAny().
                        orElse(null);
    }

    /**
     * Add tags for certificate in database method
     *
     * @param tags          {@link java.util.List} of tags
     * @param certificateId certificate id
     */
    public void add(List<Tag> tags, BigInteger certificateId) {
        for (Tag tag : tags) {
            add(tag);
            addCertificateTag(certificateId, find(tag.getName()).getId());
        }
    }

    private int addCertificateTag(BigInteger certificateId, BigInteger tagId) {
        return jdbcTemplate.update(SQL_ADD_TAG_CERTIFICATE, certificateId.longValue(), tagId.longValue());
    }

    /**
     * Add tag in database method
     *
     * @param tag tag for add
     * @return count of added rows
     */
    @Override
    public int add(Tag tag) {
        return jdbcTemplate.update(SQL_ADD, tag.getName());
    }

    /**
     * Update tag by id method
     *
     * @param id  id of the tag to update
     * @param tag tag to update
     * @return count of added rows
     */
    @Override
    public int update(BigInteger id, Tag tag) {
        throw new UnsupportedOperationException();
    }

    /**
     * Delete tag by id method
     *
     * @param id id of the tag to delete
     * @return count of deleted rows
     */
    @Override
    public int delete(BigInteger id) {
        return jdbcTemplate.update(SQL_DELETE, id.longValue());
    }
}

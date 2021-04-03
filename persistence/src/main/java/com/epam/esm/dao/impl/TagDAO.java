package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collection;

@Component
public class TagDAO implements AbstractDAO<Tag, BigInteger> {
    private final JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_ALL = "SELECT id, name FROM tag";
    private final String SQL_FIND_ALL_BY_CERTIFICATE_ID = SQL_FIND_ALL +
            " JOIN certificate_tag ON tag.id = certificate_tag.tag_id" +
            " WHERE certificate_id=?";
    private final String SQL_FIND_BY_ID = SQL_FIND_ALL+" WHERE id = ?";
    private final String SQL_ADD = "INSERT INTO tag() VALUE(?)";
    private final String SQL_UPDATE = "UPDATE tag SET name = ? where id=?";
    private final String SQL_DELETE = "DELETE FROM tag WHERE id=?";
    private final String SQL_FIND_BY_NAME = SQL_FIND_ALL+" WHERE name = ?";

    @Autowired
    public TagDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Collection<Tag> findAll(BigInteger id) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_CERTIFICATE_ID, new BeanPropertyRowMapper<>(Tag.class), id.longValue());
    }

    @Override
    public Tag find(BigInteger id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id.longValue())
                .stream().findAny().orElse(null);
    }

    @Override
    public int add(Tag tag) {
        return jdbcTemplate.update(SQL_ADD, tag.getName());
    }

    @Override
    public int update(BigInteger id, Tag tag) {
        return jdbcTemplate.update(SQL_UPDATE, tag.getName(), id.longValue());
    }

    @Override
    public int delete(BigInteger id) {
        return jdbcTemplate.update(SQL_DELETE, id.longValue());
    }
}

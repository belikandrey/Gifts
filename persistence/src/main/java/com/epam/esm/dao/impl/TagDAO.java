package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class TagDAO implements AbstractDAO<Tag> {
    private final JdbcTemplate jdbcTemplate;

    private final String SQL_GET_ALL = "SELECT * FROM tag";
    private final String SQL_GET_ALL_BY_CERTIFICATE_ID = "SELECT * FROM tag" +
            " JOIN certificate_tag ON tag.id = certificate_tag.tag_id" +
            " WHERE certificate_id=?";
    private final String SQL_GET_BY_ID = "SELECT * FROM tag WHERE id = ?";
    private final String SQL_ADD = "INSERT INTO tag() VALUE(?)";
    private final String SQL_UPDATE = "UPDATE tag SET name = ? where id=?";
    private final String SQL_DELETE = "DELETE FROM tag WHERE id=?";

    @Autowired
    public TagDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Tag> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Collection<Tag> findAll(int id) {
        return jdbcTemplate.query(SQL_GET_ALL_BY_CERTIFICATE_ID, new BeanPropertyRowMapper<>(Tag.class), id);
    }

    @Override
    public Tag find(int id) {
        return jdbcTemplate.query(SQL_GET_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id)
                .stream().findAny().orElse(null);

    }

    @Override
    public void add(Tag tag) {
        jdbcTemplate.update(SQL_ADD, tag.getName());
    }

    @Override
    public void update(int id, Tag tag) {
        jdbcTemplate.update(SQL_UPDATE, tag.getName(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }
}

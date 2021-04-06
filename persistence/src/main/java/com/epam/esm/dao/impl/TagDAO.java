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

@Component
public class TagDAO implements AbstractDAO<Tag, BigInteger> {
    private final JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_ALL = "SELECT id, name FROM tag";
    private final String SQL_FIND_ALL_BY_CERTIFICATE_ID = SQL_FIND_ALL +
            " JOIN certificate_tag ON tag.id = certificate_tag.tag_id" +
            " WHERE certificate_id=?";
    private final String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE id = ?";
    private final String SQL_ADD = "INSERT INTO tag() VALUE(?)";
    private final String SQL_UPDATE = "UPDATE tag SET name = ? where id=?";
    private final String SQL_DELETE = "DELETE FROM tag WHERE id=?";
    private final String SQL_FIND_BY_NAME = "SELECT id, name FROM tag WHERE name=?";
    private final String SQL_ADD_TAG_CERTIFICATE = "INSERT INTO certificate_tag(certificate_id, tag_id) " +
            "VALUES(?, ?)";

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

    public Tag find(String name){
        return jdbcTemplate.query(SQL_FIND_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), name)
                .stream().findAny().orElse(null);
    }

    public void add(List<Tag> tags, BigInteger certificateId){
        for(Tag tag:tags){
            add(tag);
            addCertificateTag(certificateId, find(tag.getName()).getId());
        }
    }

    public int addCertificateTag(BigInteger certificateId, BigInteger tagId){
        return jdbcTemplate.update(SQL_ADD_TAG_CERTIFICATE, certificateId.longValue(), tagId.longValue());
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

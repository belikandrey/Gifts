package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.dao.mapper.CertificateMapper;
import com.epam.esm.entity.Certificate;
import com.epam.esm.search.SearchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CertificateDAO implements AbstractDAO<Certificate, BigInteger> {
    private final JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_ALL = "SELECT id, name, description, price, duration," +
            " create_date, last_update_date FROM certificate";
    private final String SQL_FIND_ALL_BY_TAG_ID = SQL_FIND_ALL + " JOIN certificate_tag ON " +
            "certificate.id = certificate_tag.certificate_id" +
            " where certificate_tag.tag_id=?";
    private final String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE id=?";
    private final String SQL_ADD = "INSERT INTO certificate" +
            "(name, description, price, duration, create_date, last_update_date)" +
            " values(?, ?, ?, ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE certificate SET " +
            "name=?, description=?, price=?, duration=?, create_date=?," +
            " last_update_date=? where id=?";
    private final String SQL_DELETE = "DELETE FROM certificate WHERE id=?";
    private final String SQL_FIND_ALL_BY_TAG_NAME = SQL_FIND_ALL +
            " JOIN certificate_tag ON certificate.id = certificate_tag.certificate_id" +
            " JOIN tag ON certificate_tag.tag_id = tag.id WHERE tag.name = ?";

    @Autowired
    public CertificateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Collection<Certificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new CertificateMapper());
    }

    @Override
    public Collection<Certificate> findAll(BigInteger tagId) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_TAG_ID, new CertificateMapper(), tagId.longValue());
    }

    public Collection<Certificate> findAll(String tagName, String name, String description, String sortName, String sortDate) {
        final String searchQuery = getSearchQuery(tagName, name, description, sortName, sortDate);
        return jdbcTemplate.query(searchQuery, new CertificateMapper());
    }


    public Collection<Certificate> findAll(String tagName) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_TAG_NAME, new CertificateMapper(), tagName);
    }

    @Override
    public Certificate find(BigInteger id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, new CertificateMapper(), id.longValue())
                .stream().findAny().orElse(null);
    }

    @Override
    public int add(Certificate certificate) {
        return jdbcTemplate.update(SQL_ADD, certificate.getName(), certificate.getDescription(),
                certificate.getPrice(), certificate.getDuration(), certificate.getCreateDate(), certificate.getLastUpdateDate());
    }

    @Override
    public int update(BigInteger id, Certificate certificate) {
        return jdbcTemplate.update(SQL_UPDATE, certificate.getName(),
                certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), certificate.getCreateDate(),
                certificate.getLastUpdateDate(), id.longValue());
    }

    @Override
    public int delete(BigInteger id) {
        return jdbcTemplate.update(SQL_DELETE, id.longValue());
    }

    private String getSearchQuery(String tagName, String name, String description, String sortName, String sortDate) {
        SearchQueryBuilder builder = new SearchQueryBuilder();
        builder = tagName != null && !tagName.isEmpty() ? builder.setTagName(tagName) : builder;
        builder = name != null && !name.isEmpty() ? builder.setName(name) : builder;
        builder = description != null && !description.isEmpty() ? builder.setDescription(description) : builder;
        builder = sortName != null && !sortName.isEmpty() ? builder.setSortByName(sortName) :
                sortDate != null && !sortDate.isEmpty() ? builder.setSortByDate(sortDate) : builder;
        return builder.build();
    }
}

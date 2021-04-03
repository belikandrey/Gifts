package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.dao.mapper.CertificateMapper;
import com.epam.esm.entity.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CertificateDAO implements AbstractDAO<Certificate> {
    private final JdbcTemplate jdbcTemplate;

    private final String SQL_GET_ALL = "SELECT * FROM certificate";
    private final String SQL_GET_ALL_BY_TAG_ID = "SELECT * FROM certificate JOIN certificate_tag ON " +
            "certificate.id = certificate_tag.certificate_id" +
            " where certificate_tag.tag_id=?";
    private final String SQL_GET_BY_ID = "SELECT * FROM certificate WHERE id=?";
    private final String SQL_ADD = "INSERT INTO certificate" +
            "(name, description, price, duration, create_date, last_update_date)" +
            " values(?, ?, ?, ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE certificate SET " +
            "name=?, description=?, price=?, duration=?, create_date=?," +
            " last_update_date=? where id=?";
    private final String SQL_DELETE = "DELETE FROM certificate WHERE id=?";


    @Autowired
    public CertificateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Collection<Certificate> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new CertificateMapper());
    }

    @Override
    public Collection<Certificate> findAll(int id) {
        return jdbcTemplate.query(SQL_GET_ALL_BY_TAG_ID, new CertificateMapper(), id);
    }

    @Override
    public Certificate find(int id) {
        return jdbcTemplate.query(SQL_GET_BY_ID, new CertificateMapper(), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public void add(Certificate certificate) {
        jdbcTemplate.update(SQL_ADD, certificate.getName(), certificate.getDescription(),
                certificate.getPrice(), certificate.getDuration(), certificate.getCreateDate(), certificate.getLastUpdateDate());
    }

    @Override
    public void update(int id, Certificate certificate) {
        jdbcTemplate.update(SQL_UPDATE, certificate.getName(),
                certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), certificate.getCreateDate(),
                certificate.getLastUpdateDate(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }
}

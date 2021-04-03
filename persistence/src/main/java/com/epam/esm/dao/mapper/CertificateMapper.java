package com.epam.esm.dao.mapper;

import com.epam.esm.entity.Certificate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Certificate certificate = new Certificate();
        certificate.setId(BigInteger.valueOf(rs.getInt("id")));
        certificate.setName(rs.getString("name"));
        certificate.setPrice(BigDecimal.valueOf(rs.getDouble("price")));
        certificate.setDuration(rs.getInt("duration"));
        certificate.setDescription(rs.getString("description"));
        certificate.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        certificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
        return certificate;
    }
}

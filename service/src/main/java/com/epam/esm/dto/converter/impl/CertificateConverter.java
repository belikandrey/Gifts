package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import org.springframework.stereotype.Component;

@Component
public class CertificateConverter implements Converter<Certificate, CertificateDTO> {
    @Override
    public Certificate convert(CertificateDTO dto) {
        return new Certificate(dto.getId(), dto.getName(), dto.getDescription(),
                dto.getPrice(), dto.getDuration(), dto.getCreateDate(),dto.getLastUpdateDate());
    }

    @Override
    public CertificateDTO convert(Certificate entity) {
        return new CertificateDTO(entity.getId(), entity.getName(), entity.getDescription(),
                entity.getPrice(), entity.getDuration(), entity.getCreateDate(), entity.getLastUpdateDate());
    }
}

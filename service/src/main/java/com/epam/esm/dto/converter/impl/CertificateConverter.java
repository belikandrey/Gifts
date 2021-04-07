package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import org.springframework.stereotype.Component;

/**
 * Converter of certificate
 *
 * @author Andrey Belik
 * @version 1.0
 */
@Component
public class CertificateConverter implements Converter<Certificate, CertificateDTO> {
    /**
     * Convert certificate DTO to certificate
     *
     * @param dto {@link com.epam.esm.dto.CertificateDTO} to convert
     * @return {@link com.epam.esm.entity.Certificate} after convert
     */
    @Override
    public Certificate convert(CertificateDTO dto) {
        return new Certificate(dto.getId(), dto.getName(), dto.getDescription(),
                dto.getPrice(), dto.getDuration(), dto.getCreateDate(), dto.getLastUpdateDate());
    }

    /**
     * Convert certificate to certificate DTO
     *
     * @param certificate {@link com.epam.esm.entity.Certificate} to convert
     * @return {@link com.epam.esm.dto.CertificateDTO} after convert
     */
    @Override
    public CertificateDTO convert(Certificate certificate) {
        return new CertificateDTO(certificate.getId(), certificate.getName(), certificate.getDescription(),
                certificate.getPrice(), certificate.getDuration(), certificate.getCreateDate(), certificate.getLastUpdateDate());
    }
}

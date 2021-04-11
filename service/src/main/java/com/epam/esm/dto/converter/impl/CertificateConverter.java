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
   * @param dto {@link CertificateDTO} to convert
   * @return {@link Certificate} after convert
   */
  @Override
  public Certificate convert(CertificateDTO dto) {
    return new Certificate(
        dto.getId(),
        dto.getName(),
        dto.getDescription(),
        dto.getPrice(),
        dto.getDuration(),
        dto.getCreationDate(),
        dto.getLastUpdateDate());
  }

  /**
   * Convert certificate to certificate DTO
   *
   * @param certificate {@link Certificate} to convert
   * @return {@link CertificateDTO} after convert
   */
  @Override
  public CertificateDTO convert(Certificate certificate) {
    return new CertificateDTO(
        certificate.getId(),
        certificate.getName(),
        certificate.getDescription(),
        certificate.getPrice(),
        certificate.getDuration(),
        certificate.getCreateDate(),
        certificate.getLastUpdateDate());
  }
}

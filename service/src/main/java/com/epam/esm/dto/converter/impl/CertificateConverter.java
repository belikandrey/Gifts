package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

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
  private Converter<Tag, TagDTO> tagConverter;

  @Autowired
  public CertificateConverter(Converter<Tag, TagDTO> tagConverter) {
    this.tagConverter = tagConverter;
  }

  /**
   * Convert to entity certificate.
   *
   * @param dto the dto
   * @return the certificate
   */
  @Override
  public Certificate convertToEntity(CertificateDTO dto) {
    final Certificate certificate =
        new Certificate(
            dto.getId(),
            dto.getName(),
            dto.getDescription(),
            dto.getPrice(),
            dto.getDuration(),
            dto.getCreationDate(),
            dto.getLastUpdateDate());
    final Set<Tag> tags =
        dto.getTags().stream().map(tagConverter::convertToEntity).collect(Collectors.toSet());
    certificate.setTags(tags);
    return certificate;
  }

  /**
   * Convert certificate to certificate DTO
   *
   * @param certificate {@link Certificate} to convert
   * @return {@link CertificateDTO} after convert
   */
  @Override
  public CertificateDTO convertToDto(Certificate certificate) {
    final CertificateDTO certificateDTO =
        new CertificateDTO(
            certificate.getId(),
            certificate.getName(),
            certificate.getDescription(),
            certificate.getPrice(),
            certificate.getDuration(),
            certificate.getCreateDate(),
            certificate.getLastUpdateDate());
    final Set<TagDTO> tags =
        certificate.getTags().stream().map(tagConverter::convertToDto).collect(Collectors.toSet());
    certificateDTO.setTags(tags);
    return certificateDTO;
  }
}

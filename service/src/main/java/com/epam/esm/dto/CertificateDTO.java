package com.epam.esm.dto;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Certificate transfer class
 */
public class CertificateDTO {

    private BigInteger id;

    private String name;

    private String description;

    private BigDecimal price;

    private int duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    private List<TagDTO> tagsDTO;

    /**
     * Default constructor
     */
    public CertificateDTO() {
    }

    /**
     * Constructor
     *
     * @param id             id of the certificate DTO
     * @param name           name of the certificate DTO
     * @param description    description of the certificate DTO
     * @param price          price of the certificate DTO
     * @param duration       duration of the certificate DTO
     * @param createDate     create date of the certificate DTO
     * @param lastUpdateDate last update date of the certificate DTO
     */
    public CertificateDTO(BigInteger id, String name, String description, BigDecimal price,
                          int duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * Tags DTO setter
     *
     * @param tagsDTO {@link java.util.List} of {@link TagDTO}
     */
    public void setTagsDTO(List<TagDTO> tagsDTO) {
        this.tagsDTO = tagsDTO;
    }

    /**
     * Tags DTO getter
     *
     * @return {@link java.util.List} of {@link TagDTO}
     */
    public List<TagDTO> getTagsDTO() {
        return tagsDTO;
    }

    /**
     * Id getter
     *
     * @return id of the certificate DTO
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Id setter
     *
     * @param id of the certificate DTO
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

    /**
     * Name getter
     *
     * @return name of the certificate DTO
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter
     *
     * @param name name of the certificate DTO
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Description getter
     *
     * @return description of the certificate DTO
     */
    public String getDescription() {
        return description;
    }

    /**
     * Description setter
     *
     * @param description description of the certificate DTO
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Price getter
     *
     * @return price of the certificate DTO
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Price setter
     *
     * @param price price of the certificate DTO
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Duration getter
     *
     * @return duration of the certificate DTO
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Duration setter
     *
     * @param duration duration of the certificate DTO
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Create date getter
     *
     * @return create date of the certificate DTO
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Create date setter
     *
     * @param createDate create date of the certificate DTO
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Last update date getter
     *
     * @return last update date of the certificate DTO
     */
    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Last update date setter
     *
     * @param lastUpdateDate last update date of the certificate DTO
     */
    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateDTO that = (CertificateDTO) o;
        return duration == that.duration && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(createDate, that.createDate) && Objects.equals(lastUpdateDate, that.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate);
    }

    @Override
    public String toString() {
        return "CertificateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}

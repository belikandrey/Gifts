package com.epam.esm.search;

public class SearchQueryBuilder {
    private static final String SQL_BASIC_QUERY = "SELECT certificate.id, certificate.name, certificate.description, " +
            "certificate.price, certificate.duration," +
            " certificate.create_date, certificate.last_update_date FROM gifts.certificate" +
            " JOIN gifts.certificate_tag ON certificate.id = certificate_tag.certificate_id" +
            " JOIN gifts.tag ON certificate_tag.tag_id = tag.id ";
    private static final String SQL_SET_TAG_NAME = " tag.name = '";
    private static final String SQL_SET_NAME = " certificate.name LIKE '%";
    private static final String SQL_SET_DESCRIPTION = " certificate.description LIKE '%";
    private static final String SQL_SET_SORT_BY_NAME = " ORDER BY certificate.name ";
    private static final String SQL_SET_SORT_BY_DATE = " ORDER BY certificate.create_date ";

    private StringBuilder stringBuilder;
    private boolean isComposite;

    public SearchQueryBuilder() {
        stringBuilder = new StringBuilder(SQL_BASIC_QUERY);
    }

    public String build() {
        return stringBuilder.toString();
    }

    public SearchQueryBuilder setTagName(String tagName) {
        final Separator separator = getSeparator();
        stringBuilder.append(separator.getValue() + SQL_SET_TAG_NAME + tagName + "' ");
        return this;
    }

    public SearchQueryBuilder setName(String name) {
        final Separator separator = getSeparator();
        stringBuilder.append(separator.getValue() + SQL_SET_NAME + name + "%' ");
        return this;
    }

    public SearchQueryBuilder setDescription(String description) {
        final Separator separator = getSeparator();
        stringBuilder.append(separator.getValue() + SQL_SET_DESCRIPTION + description + "%' ");
        return this;
    }

    public SearchQueryBuilder setSortByName(String typeOfSort) {
        stringBuilder.append(SQL_SET_SORT_BY_NAME + typeOfSort.toUpperCase());
        return this;
    }

    public SearchQueryBuilder setSortByDate(String typeOfSort) {
        stringBuilder.append(SQL_SET_SORT_BY_DATE + typeOfSort.toUpperCase());
        return this;
    }

    private final Separator getSeparator() {
        final Separator separator = Separator.getSeparator(isComposite);
        isComposite = true;
        return separator;
    }
}

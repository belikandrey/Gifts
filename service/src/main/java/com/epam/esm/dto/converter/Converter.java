package com.epam.esm.dto.converter;

import com.epam.esm.entity.Entity;

public interface Converter<T extends Entity, K> {
    T convert(K dto);
    K convert(T entity);
}

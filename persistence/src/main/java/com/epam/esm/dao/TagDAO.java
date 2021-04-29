package com.epam.esm.dao;

import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.entity.Tag;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface TagDAO {

  Optional<Tag> findById(BigInteger id);

  List<Tag> findAll(PaginationSetting paginationSetting);

  Tag save(Tag entity);

  void deleteById(BigInteger id);

  List<Tag> findTagsByCertificateId(BigInteger certificateId);

  Optional<Tag> findTagByName(String name);

  Long count();

  Optional<Tag> findMostPopularTag();

  boolean isTagUsed(BigInteger tagId);
}

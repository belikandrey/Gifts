package com.epam.esm.dao;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.entity.Tag;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface TagDAO {

  Optional<Tag> findById(BigInteger id);

  List<Tag> findAll(Pageable pageable);

  Tag save(Tag entity);

  Tag update(Tag entity);

  void deleteById(BigInteger id);

  List<Tag> findTagsByCertificateId(BigInteger certificateId);

  Optional<Tag> findTagByName(String name);

    boolean isAlreadyExist(Tag tag);
}

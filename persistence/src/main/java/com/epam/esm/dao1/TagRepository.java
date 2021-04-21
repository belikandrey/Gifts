package com.epam.esm.dao1;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, BigInteger> {
    Tag findTagByName(String name);
    boolean existsTagByIdOrName(BigInteger id, String name);
    Set<Tag> findTagsByCertificateId(BigInteger certificate_id);
}

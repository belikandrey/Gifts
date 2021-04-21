package com.epam.esm.dao1;

import com.epam.esm.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface CertificateRepository extends JpaRepository<Certificate, BigInteger> {

}

package com.epam.esm.service;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.dto.UserDTO;

import java.math.BigInteger;
import java.util.List;

public interface UserService extends EntityService<UserDTO, BigInteger>{
    List<UserDTO> findAll(Pageable pageable);
}

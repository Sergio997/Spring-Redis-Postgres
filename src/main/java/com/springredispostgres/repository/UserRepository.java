package com.springredispostgres.repository;

import com.springredispostgres.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>,
        ReferenceRepository<User> {

    List<User> findAll();

}

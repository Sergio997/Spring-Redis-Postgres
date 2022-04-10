package com.springredispostgres.repository;

import java.util.Optional;
import java.util.UUID;

public interface ReferenceRepository<T> {

    Optional<T> findByReference(UUID reference);

    boolean existsByReference(UUID reference);

}

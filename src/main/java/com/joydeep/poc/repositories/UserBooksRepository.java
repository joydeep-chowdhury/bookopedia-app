package com.joydeep.poc.repositories;

import com.joydeep.poc.models.UserBooks;
import com.joydeep.poc.models.UserBooksPrimaryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBooksRepository extends CassandraRepository<UserBooks, UserBooksPrimaryKey> {
    Slice<UserBooks> findAllById(String id, Pageable pageable);
}

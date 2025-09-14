package com.fastcampus.testdata.repository;

import com.fastcampus.testdata.domain.TableSchema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableSchemaRepository extends JpaRepository<TableSchema, Long> {
}


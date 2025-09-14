package com.fastcampus.testdata.repository;

import com.fastcampus.testdata.domain.SchemaField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchemaFieldRepository extends JpaRepository<SchemaField, Long> {
}

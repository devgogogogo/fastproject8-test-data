package com.fastcampus.testdata.repository;

import com.fastcampus.testdata.domain.MockData;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MockDataRepository extends JpaRepository<MockData, Long> {
}

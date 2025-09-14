package com.fastcampus.testdata.repository;

import com.fastcampus.testdata.domain.MockData;
import com.fastcampus.testdata.domain.constant.MockDataType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface MockDataRepository extends JpaRepository<MockData, Long> {
    @Cacheable("mockData")
    List<MockData> findByMockDataType(MockDataType mockDataType);
}

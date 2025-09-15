package com.fastcampus.testdata.dto;

import com.fastcampus.testdata.domain.MockData;
import com.fastcampus.testdata.domain.constant.MockDataType;

public record MockDataDto(Long id,MockDataType mockDataType, String mockDataValue) {

    public static MockDataDto fromEntity(MockData entity) {
        return new MockDataDto(
                entity.getId(),
                entity.getMockDataType(),
                entity.getMockDataValue()
        );
    }


}

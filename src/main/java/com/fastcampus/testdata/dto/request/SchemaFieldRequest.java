package com.fastcampus.testdata.dto.request;

import com.fastcampus.testdata.domain.constant.MockDataType;
import com.fastcampus.testdata.dto.SchemaFieldDto;

public record SchemaFieldRequest(
        String fieldName,
        MockDataType mockDataType,
        Integer fieldOrder,
        Integer blankPercent,
        String typeOptionJson,
        String forceValue
) {

    public SchemaFieldDto toDto() {
        return SchemaFieldDto.of(
                fieldName,
                mockDataType,
                fieldOrder,
                blankPercent,
                typeOptionJson,
                forceValue
        );
    }
}

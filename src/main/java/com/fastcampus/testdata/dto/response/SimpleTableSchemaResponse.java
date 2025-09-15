package com.fastcampus.testdata.dto.response;

import com.fastcampus.testdata.dto.TableSchemaDto;

import java.time.LocalDateTime;

public record SimpleTableSchemaResponse(
        String schemaName,
        String userId,
        LocalDateTime modifiedAt
) {

    public static SimpleTableSchemaResponse fromDto(TableSchemaDto dto) {
        return new SimpleTableSchemaResponse(dto.schemaName(), dto.userId(), dto.modifiedAt());
    }

}

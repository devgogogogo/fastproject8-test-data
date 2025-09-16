package com.fastcampus.testdata.dto.request;

import com.fastcampus.testdata.dto.TableSchemaDto;

import java.util.List;
import java.util.stream.Collectors;


public record TableSchemaRequest(
        String schemaName,
        List<SchemaFieldRequest> schemaFields
) {

    public static TableSchemaRequest of(String schemaName, List<SchemaFieldRequest> schemaFields) {
        return new TableSchemaRequest(schemaName, schemaFields);
    }

    public TableSchemaDto toDto(String userId) {
        return TableSchemaDto.of(
                schemaName,
                userId,
                null,
                schemaFields.stream()
                        .map(SchemaFieldRequest::toDto)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

}

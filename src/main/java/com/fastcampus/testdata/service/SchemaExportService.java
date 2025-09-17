package com.fastcampus.testdata.service;

import com.fastcampus.testdata.domain.TableSchema;
import com.fastcampus.testdata.domain.constant.ExportFileType;
import com.fastcampus.testdata.dto.TableSchemaDto;
import com.fastcampus.testdata.repository.TableSchemaRepository;
import com.fastcampus.testdata.service.exporter.MockDataFileExporterContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SchemaExportService {

    private final MockDataFileExporterContext mockDataFileExporterContext;
    private final TableSchemaRepository tableSchemaRepository;

    public String export(ExportFileType fileType, TableSchemaDto dto, Integer rowCount) {
        if (dto.userId() != null) {
            tableSchemaRepository.findByUserIdAndSchemaName(dto.userId(), dto.schemaName())
                    .ifPresent(TableSchema::markExported);
        }

        return mockDataFileExporterContext.export(fileType, dto, rowCount);
    }

}

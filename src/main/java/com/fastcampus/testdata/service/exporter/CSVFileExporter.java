package com.fastcampus.testdata.service.exporter;

import com.fastcampus.testdata.domain.constant.ExportFileType;
import com.fastcampus.testdata.service.generator.MockDataGeneratorContext;
import org.springframework.stereotype.Component;

@Component
public class CSVFileExporter extends DelimiterBasedFileExporter implements MockDataFileExporter {

    public CSVFileExporter(MockDataGeneratorContext mockDataGeneratorContext) {
        super(mockDataGeneratorContext);
    }

    @Override
    public String getDelimiter() {
        return ",";
    }

    @Override
    public ExportFileType getType() {
        return ExportFileType.CSV;
    }

}
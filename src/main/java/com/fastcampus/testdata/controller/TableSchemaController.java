package com.fastcampus.testdata.controller;

import com.fastcampus.testdata.domain.constant.ExportFileType;
import com.fastcampus.testdata.domain.constant.MockDataType;
import com.fastcampus.testdata.dto.request.TableSchemaExportRequest;
import com.fastcampus.testdata.dto.request.TableSchemaRequest;
import com.fastcampus.testdata.dto.response.SchemaFieldResponse;
import com.fastcampus.testdata.dto.response.TableSchemaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class TableSchemaController {

    private final ObjectMapper mapper;

    @GetMapping("/table-schema")
    public String tableSchema(Model model) {
        TableSchemaResponse tableSchema = defaultTableSchema();

        model.addAttribute("tableSchema", tableSchema);
        model.addAttribute("mockDataType", MockDataType.toObjects());
        model.addAttribute("fileType", Arrays.stream(ExportFileType.values()).toList());
        return "table-schema";
    }



    @PostMapping("/table-schema")
    public String createOrUpdateTableSchema(
            TableSchemaRequest tableSchemaRequest,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("tableSchemaRequest", tableSchemaRequest);

        return "redirect:/table-schema";
    }

    @GetMapping("/table-schema/my-schemas")
    public String mySchemas() {
        return "my-schemas";
    }

    @PostMapping("/table-schema/my-schemas/{schemaName}")
    public String deleteMySchema(
            @PathVariable String schemaName,
            RedirectAttributes redirectAttributes
    ) {
        return "redirect:/my-schemas";
    }

    @GetMapping("/table-schema/export")
    public ResponseEntity<String> exportTableSchema(TableSchemaExportRequest tableSchemaExportRequest) throws JsonProcessingException {

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=table-schema.txt")
                .body(json(tableSchemaExportRequest));
    }

    /**
     * Content-Disposition 헤더
     * inline : 브라우저 화면에 바로 보여줘라 (예: PDF 뷰어에서 열림).
     * attachment : 다운로드해야 하는 파일로 취급해라.
     * filename=... : 그 파일의 기본 이름은 이걸로 해라.
     */

    private TableSchemaResponse defaultTableSchema() {
        return new TableSchemaResponse(
                "schema",
                "Uno",
                List.of(
                        new SchemaFieldResponse("fieldName1", MockDataType.STRING, 1, 0, null, null),
                        new SchemaFieldResponse("fieldName2", MockDataType.NUMBER, 2, 10, null, null),
                        new SchemaFieldResponse("fieldName3", MockDataType.NAME, 3, 20, null, null)
                )
        );
    }

    private String json(Object object) {
        try {
            return mapper.writeValueAsString(object);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

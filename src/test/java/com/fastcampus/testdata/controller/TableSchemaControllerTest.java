package com.fastcampus.testdata.controller;

import com.fastcampus.testdata.config.SecurityConfig;
import com.fastcampus.testdata.domain.constant.ExportFileType;
import com.fastcampus.testdata.domain.constant.MockDataType;
import com.fastcampus.testdata.dto.request.SchemaFieldRequest;
import com.fastcampus.testdata.dto.request.TableSchemaExportRequest;
import com.fastcampus.testdata.dto.request.TableSchemaRequest;
import com.fastcampus.testdata.util.FormDataEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("[Controller] 테이블 스키마 컨트롤러 테스트")
@Import({SecurityConfig.class, FormDataEncoder.class})
@WebMvcTest
public record TableSchemaControllerTest(
        @Autowired MockMvc mockMvc,
        @Autowired FormDataEncoder formDataEncoder,
        @Autowired ObjectMapper mapper
) {

    @DisplayName("[GET] 테이블 스키마 조회 -> 비로그인 최고 진입(정상)")
    @Test
    void given_whenRequesting_thenShowTableschemaView() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(get("/table-schema"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("tableSchema"))
                .andExpect(model().attributeExists("mockDataType"))
                .andExpect(model().attributeExists("fileType"))
                .andExpect(view().name("table-schema"));
    }

    @DisplayName("[GET] 테이블 스키마 조회, 로그인 + 특정 테이블 스키마 (정상)")
    @Test
    void givenAuthenticatedUserAndSchemaName_whenRequesting_thenShowsTableSchemaView() throws Exception {
        // Given

        String schemaName = "test_schema";

        // When & Then
        mockMvc.perform(
                        get("/table-schema")
                                .queryParam("schemaName", schemaName)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("tableSchema"))
//                .andExpect(model().attribute("tableSchema", hasProperty("schemaName", is(schemaName)))) // dto가 record여서 불가능한 방식
                .andExpect(model().attributeExists("mockDataTypes"))
                .andExpect(model().attributeExists("fileTypes"))
                .andExpect(content().string(containsString(schemaName))) // html 전체 검사하므로 정확하지 않은 테스트 방식
                .andExpect(view().name("table-schema"));

    }

    /**
     * 네가 /table-schema로 POST 요청을 보냄 → “테이블 만들어줘!”
     * 서버가 그 요청을 처리하고 나서 말해 →
     * 👉 “응 처리했어. 그런데 결과는 여기서 안 줄 거야.
     * 👉 대신 /table-schema 주소로 다시 가서 봐!”
     * 그래서 브라우저는 다시 /table-schema로 GET 요청을 보냄.
     * 사용자가 새로고침을 해도 “테이블 다시 만들어줘!” 같은 POST 요청이 두 번 실행되는 걸 막기 위해서야.
     */
    @DisplayName("[POST] 테이블 스키마 생성, 변경 (정상)")
    @Test
    void givenTableSchemaRequest_whenCreatingOrUpdating_thenRedirectsToTableSchemaView() throws Exception {
        TableSchemaRequest request = TableSchemaRequest.of(
                "test_schema",
                List.of(
                        SchemaFieldRequest.of("id", MockDataType.ROW_NUMBER, 1, 0, null, null),
                        SchemaFieldRequest.of("name", MockDataType.NAME, 2, 10, null, null),
                        SchemaFieldRequest.of("age", MockDataType.NUMBER, 3, 20, null, null)
                )
        );

        mockMvc.perform(
                        post("/table-schema")
                                .content(formDataEncoder.encode(request)) // 여기는 나중에 바꿔야함
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("tableSchemaRequest", request))
                .andExpect(redirectedUrl("/table-schema"));
    }

    @DisplayName("[GET] 내 스키마 목록 조회(정상)")
    @Test
    void givenAuthenticatedUser_whenRequesting_thenShowMySchemaView() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(get("/table-schema/my-schemas"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("tableSchema"))
                .andExpect(view().name("my-schemas"));
    }

    @DisplayName("[POST] 내 스키마 삭제(정상)")
    @Test
    void givenAuthenticatedUserAndSchemaName_whenDeleting_thenRedirectsToTableSchemaView() throws Exception {
        //Given
        String schemaName = "test_schema";

        //When & Then
        mockMvc.perform(
                        post("/table-schema/my-schemas/{schemaName}", schemaName)
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/table-schema/my-schemas"));
    }

    @DisplayName("[GET] 테이블 스키마 파일 다운로드 (정상)")
    @Test
    void givenTableSchema_whenDownloading_thenReturnsFile() throws Exception {
        //Given
        TableSchemaExportRequest request = TableSchemaExportRequest.of(
                "test",
                77,
                ExportFileType.JSON,
                List.of(
                        SchemaFieldRequest.of("id", MockDataType.ROW_NUMBER, 1, 0, null, null),
                        SchemaFieldRequest.of("name", MockDataType.STRING, 1, 0, "option", "well"),
                        SchemaFieldRequest.of("age", MockDataType.NUMBER, 3, 20, null, null)
                )
        );

        String queryParam = formDataEncoder.encode(request, false);
        //When & Then
        mockMvc.perform(get("/table-schema/export?" + queryParam))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=table-schema.txt"))
                .andExpect(content().json(mapper.writeValueAsString(request)));
    }
}

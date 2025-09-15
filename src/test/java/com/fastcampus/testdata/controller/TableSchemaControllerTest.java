package com.fastcampus.testdata.controller;

import com.fastcampus.testdata.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("[Controller] 테이블 스키마 컨트롤러 테스트")
@Import(SecurityConfig.class)
@WebMvcTest
public record TableSchemaControllerTest(@Autowired MockMvc mockMvc) {

    @DisplayName("[GET] 테이블 스키마 페이지 -> 테이블 스키마 뷰 (정상)")
    @Test
    void given_whenRequesting_thenShowTableschemaView() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(get("/table-schema"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
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
        mockMvc.perform(
                        post("/table-schema")
                                .content("sample data") // 여기는 나중에 바꿔야함
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/table-schema"));
    }

    @DisplayName("[GET] 내 스키마 목록 페이지 -> 내 스키마 목록 뷰(정상)")
    @Test
    void givenAuthenticatedUser_whenRequesting_thenShowMySchemaView() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(get("/table-schema/my-schemas"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
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
                .andExpect(redirectedUrl("/my-schemas"));
    }

    @DisplayName("[GET] 테이블 스키마 파일 다운로드 -> 테이블 스키마 파일 (정상)")
    @Test
    void givenTableSchema_whenDownloading_thenReturnsFile() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(get("/table-schema/export"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(header().string("Content-Disposition", "attachment; filename=table-schema.txt"))
                .andExpect(content().string("download complete!")); //TODO 나중에 데이터 바꿔야 함.
    }
}

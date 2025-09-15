package com.fastcampus.testdata.controller;

import com.fastcampus.testdata.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@DisplayName("[Controller] 메인 컨트롤러 테스트")
@Import(SecurityConfig.class)
@WebMvcTest(MainController.class)
record MainControllerTest(
        @Autowired MockMvc mockMvc
) {


    @DisplayName("[GET] 메인(루트) 페이지 -> 메인 뷰 (정상)")
    @Test
    void givenNothing_whenEnteringRootPage_thenShowsMainView() throws Exception {
        //given

        //when & then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("index"));
    }
}
package org.sopt.practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.sopt.practice.dto.BlogCreateRequest;
import org.sopt.practice.repository.BlogRepository;
import org.sopt.practice.repository.MemberRepository;
import org.sopt.practice.service.BlogService;
import org.sopt.practice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestHeader;

import java.awt.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BlogController.class) //SpringBootTest로 서버를 구동시키는 대신 Controller 계층만 테스트
@AutoConfigureMockMvc //Spring Boot 테스트에서 MockMvc를 사용하기 위한 설정을 자동으로 제공하는 어노테이션
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private BlogService blogService;

    @SpyBean
    private MemberService memberService;

    @MockBean
    private BlogRepository blogRepository;

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class createBlog {
        @Test
        @DisplayName("Blog 생성 실패 테스트")
        public void createBlogFail() throws Exception {

            //given
            String request = objectMapper.writeValueAsString(new BlogCreateRequest("의진 블로그", "블로그"));

            //when
            mockMvc.perform(
                    post("/api/v1/blog")
                            .content(request).header("memberId", 2)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }
}

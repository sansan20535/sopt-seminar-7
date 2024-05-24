package org.sopt.practice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.auth.PrincipalHandler;
import org.sopt.practice.common.dto.SuccessMessage;
import org.sopt.practice.common.dto.SuccessStatusResponse;
import org.sopt.practice.dto.BlogCreateRequest;
import org.sopt.practice.dto.BlogTitleUpdateRequest;
import org.sopt.practice.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

//    @PostMapping("/blog")
//    public ResponseEntity<SuccessStatusResponse> createBlog(
//            // memberId를 명시 안하면 오류
//            @RequestHeader("memberId") Long memberId,
//            @RequestBody BlogCreateRequest blogCreateRequest
//    ) {
//        //ResponseEntity.created()를 사용하면 body에 값을 담을 수가 없게 됨
//        return ResponseEntity.status(HttpStatus.CREATED).header(
//                        "Location",
//                        blogService.create(memberId, blogCreateRequest))
//                .body(SuccessStatusResponse.of(SuccessMessage.BLOG_CREATE_SUCCESS));
//    }

    @PatchMapping("/blog/{blogId}/title")
    public ResponseEntity updateBlogTitle(
            @PathVariable("blogId") Long blogId,
            @Valid @RequestBody BlogTitleUpdateRequest blogTitleUpdateRequest
    ) {
        blogService.updateTitle(blogId, blogTitleUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    private final PrincipalHandler principalHandler;

    @PostMapping("/blog")
    public ResponseEntity createBlog(
            @ModelAttribute BlogCreateRequest blogCreateRequest
    ) {
        return ResponseEntity.created(URI.create(blogService.create(
                principalHandler.getUserIdFromPrincipal(), blogCreateRequest))).build();
    }
}

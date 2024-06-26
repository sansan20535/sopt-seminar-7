package org.sopt.practice.service;

import lombok.RequiredArgsConstructor;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.domain.Blog;
import org.sopt.practice.domain.Member;
import org.sopt.practice.dto.BlogCreateRequest;
import org.sopt.practice.dto.BlogTitleUpdateRequest;
import org.sopt.practice.exception.NotFoundException;
import org.sopt.practice.external.S3Service;
import org.sopt.practice.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final MemberService memberService;
    private final S3Service s3Service;
    private static final String BLOG_S3_UPLOAD_FOLER = "blog/";

//    public String create(Long memberId, BlogCreateRequest createRequest) {
//        //member찾기
//        Member member = memberService.findById(memberId);
//        Blog blog = blogRepository.save(Blog.create(member, createRequest));
//        return blog.getId().toString();
//    }

    @Transactional
    public void updateTitle(Long blogId, BlogTitleUpdateRequest blogTitleUpdateRequest) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BLOG_NOT_FOUND_BY_ID_EXCEPTION)
        );
        blog.updateTitle(blogTitleUpdateRequest.title());
    }

    @Transactional
    public String create(Long memberId, BlogCreateRequest createRequest) {
        //member찾기
        Member member = memberService.findById(memberId);
        try {
            Blog blog = blogRepository.save(Blog.create(member, createRequest.title(), createRequest.description(),
                    s3Service.uploadImage(BLOG_S3_UPLOAD_FOLER, createRequest.image())));
            return blog.getId().toString();
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

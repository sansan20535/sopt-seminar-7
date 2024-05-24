package org.sopt.practice.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.practice.dto.UserJoinResponse;
import org.sopt.practice.service.MemberService;
import org.sopt.practice.dto.MemberCreateDto;
import org.sopt.practice.dto.MemberFindDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

//    @PostMapping
//    public ResponseEntity createMember(
//            @RequestBody MemberCreateDto memberCreateDto
//    ){
//        return ResponseEntity.created(URI.create(memberService.createMember(memberCreateDto))).build();
//    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberFindDto> findMemberById(@PathVariable("memberId") Long memberId){
        return ResponseEntity.ok(memberService.findMemberById(memberId));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteMemberById(@PathVariable("memberId") Long memberId){
        memberService.deleteMemberById(memberId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UserJoinResponse> postMember(
            @RequestBody MemberCreateDto memberCreate
    ) {
        UserJoinResponse userJoinResponse = memberService.createMember(memberCreate);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", userJoinResponse.userId())
                .body(
                        userJoinResponse
                );
    }
}

package org.sopt.practice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.auth.UserAuthentication;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.common.jwt.JwtTokenProvider;
import org.sopt.practice.domain.Member;
import org.sopt.practice.dto.UserJoinResponse;
import org.sopt.practice.exception.NotFoundException;
import org.sopt.practice.repository.MemberRepository;
import org.sopt.practice.dto.MemberCreateDto;
import org.sopt.practice.dto.MemberFindDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

//    @Transactional
//    public String createMember(
//            MemberCreateDto memberCreateDto
//    ){
//        Member member = Member.create(memberCreateDto.name(), memberCreateDto.part(), memberCreateDto.age());
//        memberRepository.save(member);
//        return member.getId().toString();
//    }

    // @Transactional(readOnly = true) 이것을 사용해도 됨
    public MemberFindDto findMemberById(Long memberId){
        return MemberFindDto.of(memberRepository.findById(memberId).orElseThrow(
                () -> new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다.")
        ));
    }

    @Transactional
    public void deleteMemberById(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다."));
        memberRepository.delete(member);
    }

    public Member findById(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND_BY_ID_EXCEPTION)
        );
    }

    @Transactional
    public UserJoinResponse createMember(
            MemberCreateDto memberCreate
    ) {
        Member member = memberRepository.save(
                Member.create(memberCreate.name(), memberCreate.part(), memberCreate.age())
        );
        Long memberId = member.getId();
        String accessToken = jwtTokenProvider.issueAccessToken(
                UserAuthentication.createUserAuthentication(memberId)
        );
        return UserJoinResponse.of(accessToken, memberId.toString());
    }
}

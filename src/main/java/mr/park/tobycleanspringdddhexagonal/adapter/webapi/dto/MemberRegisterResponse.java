package mr.park.tobycleanspringdddhexagonal.adapter.webapi.dto;

import mr.park.tobycleanspringdddhexagonal.domain.member.Member;

public record MemberRegisterResponse(Long memberId, String email) {
    public static MemberRegisterResponse of(Member member) {
        return new MemberRegisterResponse(member.getId(), member.getEmail().address());
    }
}

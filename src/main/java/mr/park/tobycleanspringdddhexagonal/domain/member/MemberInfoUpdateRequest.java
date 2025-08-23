package mr.park.tobycleanspringdddhexagonal.domain.member;

public record MemberInfoUpdateRequest(
        String nickname,
        String profileAddress,
        String introduction) {
}

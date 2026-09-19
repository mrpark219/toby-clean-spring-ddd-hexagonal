package mr.park.tobycleanspringdddhexagonal.domain.member;


import mr.park.tobycleanspringdddhexagonal.application.member.provided.MemberRegisterRequest;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixture {
    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "mrpark219", "longSecret");
    }

    public static MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest("mrpark219@gmail.com");
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }

    public static Member createMember() {
        return Member.register(createMemberRegisterRequest().toInfo(), createPasswordEncoder());
    }

    public static Member createMember(Long id) {
        Member member = Member.register(createMemberRegisterRequest().toInfo(), createPasswordEncoder());
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }


    public static Member createMember(String email) {
        return Member.register(createMemberRegisterRequest(email).toInfo(), createPasswordEncoder());
    }

    public static Member createActiveMember() {
        Member member = createMember();
        member.activate();
        return member;
    }
}

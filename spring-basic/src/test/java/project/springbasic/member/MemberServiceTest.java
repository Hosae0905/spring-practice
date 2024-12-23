package project.springbasic.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.springbasic.AppConfig;

public class MemberServiceTest {

//    MemberService memberService = new MemberServiceImpl();

    /**
     * AppConfig 클래스를 통한 의존성 주입 후 테스트 실행
     */

    MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();          // 애플리케이션 구성 정보를 생성한다.
        memberService = appConfig.memberService();      // AppConfig 객체가 생성되면서 MemberService 구현체를 주입 받을 수 있게 된다.
    }

    @Test
    void join() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}

package project.springbasic.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.springbasic.AppConfig;
import project.springbasic.member.Grade;
import project.springbasic.member.Member;
import project.springbasic.member.MemberService;
import project.springbasic.member.MemberServiceImpl;

public class OrderServiceTest {

//    MemberService memberService = new MemberServiceImpl();
//    OrderService orderService = new OrderServiceImpl();

    /**
     * AppConfig 클래스를 통한 의존성 주입 후 테스트 실행
     */

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        //given
        Long memberId = 1L;
        Member memberA = new Member(memberId, "memberA", Grade.VIP);

        //when
        memberService.join(memberA);
        Order order = orderService.createOrder(memberId, "itemA", 10000);

        //then
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}

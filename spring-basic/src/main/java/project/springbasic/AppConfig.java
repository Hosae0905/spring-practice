package project.springbasic;

import project.springbasic.discount.FixDiscountPolicy;
import project.springbasic.member.MemberService;
import project.springbasic.member.MemberServiceImpl;
import project.springbasic.member.MemoryMemberRepository;
import project.springbasic.order.OrderService;
import project.springbasic.order.OrderServiceImpl;

public class AppConfig {

    /**
     * 생성자 주입을 통해서 책임을 분리한다.
     * @return MemberServiceImpl 객체
     */
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    /**
     * 생성자 주입을 통해서 책임을 분리한다.
     * @return OrderServiceImpl 객체
     */
    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}

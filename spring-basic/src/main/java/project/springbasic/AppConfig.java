package project.springbasic;

import project.springbasic.discount.DiscountPolicy;
import project.springbasic.discount.FixDiscountPolicy;
import project.springbasic.discount.RateDiscountPolicy;
import project.springbasic.member.MemberRepository;
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
//    public MemberService memberService() {
//        return new MemberServiceImpl(new MemoryMemberRepository());
//    }

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    /**
     * MemberRepository 역할이 드러나게 바꿔준다. --> 이를 통해서 역할과 구현 클래스(MemoryMemberRepository)가 한 눈에 들어온다.
     * 역할을 드러냈기 때문에 추후 메모리에서 DB로 변경될 시 해당 코드만 변경하면 된다.
     * @return MemberRepository
     */
    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    /**
     * 생성자 주입을 통해서 책임을 분리한다.
     * @return OrderServiceImpl 객체
     */
//    public OrderService orderService() {
//        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
//    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    /**
     * DiscountPolicy 역할이 드러나게 바꿔준다. --> 이를 통해서 역할과 구현 클래스(FixDiscountPolicy)가 한 눈에 들어온다.
     * 할인 정책이 변경되면 해당 코드만 변경하면 된다.
     * @return DiscountPolicy
     */
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();        // 새로운 할인 정책을 적용하려면 해당 코드만 변경하면 된다.
    }
}

package project.springbasic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.springbasic.discount.DiscountPolicy;
import project.springbasic.discount.FixDiscountPolicy;
import project.springbasic.discount.RateDiscountPolicy;
import project.springbasic.member.MemberRepository;
import project.springbasic.member.MemberService;
import project.springbasic.member.MemberServiceImpl;
import project.springbasic.member.MemoryMemberRepository;
import project.springbasic.order.OrderService;
import project.springbasic.order.OrderServiceImpl;

/**
 * @Configuration은 애플리케이션의 설정 정보를 담은 클래스를 의미한다.
 */
@Configuration
public class AppConfig {

    /**
     * 생성자 주입을 통해서 책임을 분리한다.
     * @return MemberServiceImpl 객체
     */
//    public MemberService memberService() {
//        return new MemberServiceImpl(new MemoryMemberRepository());
//    }

    /**
     * 스프링 적용하기 전
     * @return MemberServiceImpl 객체
     */
//    public MemberService memberService() {
//        return new MemberServiceImpl(memberRepository());
//    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    /**
     * 스프링 적용하기 전
     * MemberRepository 역할이 드러나게 바꿔준다. --> 이를 통해서 역할과 구현 클래스(MemoryMemberRepository)가 한 눈에 들어온다.
     * 역할을 드러냈기 때문에 추후 메모리에서 DB로 변경될 시 해당 코드만 변경하면 된다.
     * @return MemberRepository
     */
//    private MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    /**
     * 생성자 주입을 통해서 책임을 분리한다.
     * @return OrderServiceImpl 객체
     */
//    public OrderService orderService() {
//        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
//    }

    /**
     * 스프링 적용하기 전
     * @return OrderServiceImpl 객체
     */
//    public OrderService orderService() {
//        return new OrderServiceImpl(memberRepository(), discountPolicy());
//    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    /**
     * 스프링 적용하기 전
     * DiscountPolicy 역할이 드러나게 바꿔준다. --> 이를 통해서 역할과 구현 클래스(FixDiscountPolicy)가 한 눈에 들어온다.
     * 할인 정책이 변경되면 해당 코드만 변경하면 된다.
     * @return DiscountPolicy
     */
//    public DiscountPolicy discountPolicy() {
////        return new FixDiscountPolicy();
//        return new RateDiscountPolicy();        // 새로운 할인 정책을 적용하려면 해당 코드만 변경하면 된다.
//    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();        // 새로운 할인 정책을 적용하려면 해당 코드만 변경하면 된다.
    }
}

package project.springbasic.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.springbasic.discount.DiscountPolicy;
import project.springbasic.discount.FixDiscountPolicy;
import project.springbasic.discount.RateDiscountPolicy;
import project.springbasic.member.Member;
import project.springbasic.member.MemberRepository;
import project.springbasic.member.MemoryMemberRepository;

@Component
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /*
    * DIP와 OCP를 위반하는 사례
    * 새로운 할인 정책을 적용했지만 추상 뿐만 아니라 구현 클래스에도 의존하기 때문에
    * 클라이언트 코드인 OrderServiceImpl 클래스에서 코드 변경이 이루어지는 문제가 발생한다.
    * */
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

//    private DiscountPolicy discountPolicy;      // 인터페이스만 의존하게 변경 --> 하지만 구현체가 없기 때문에 널 포인터 예외 발생

    // 의존 관계 주입을 필드에서 직접 주입할 수 있다.
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;

    // 수정자 주입 방식
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }
//
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 생성자를 주입해서 인터페이스만 의존하게 만든다.
     * @param memberRepository
     * @param discountPolicy
     */

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

//    @Autowired        // 생성자가 1개면 생략 가능
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}

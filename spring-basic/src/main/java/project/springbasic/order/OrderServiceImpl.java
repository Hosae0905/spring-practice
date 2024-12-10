package project.springbasic.order;

import project.springbasic.discount.DiscountPolicy;
import project.springbasic.discount.FixDiscountPolicy;
import project.springbasic.member.Member;
import project.springbasic.member.MemberRepository;
import project.springbasic.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}

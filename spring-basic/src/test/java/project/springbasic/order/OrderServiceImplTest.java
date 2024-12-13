package project.springbasic.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import project.springbasic.discount.FixDiscountPolicy;
import project.springbasic.member.Grade;
import project.springbasic.member.Member;
import project.springbasic.member.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    @Test
    void createOrder() {
        /**
         * 수정자 주입 방식을 통해 의존관게를 설정하고 테스트를 하면
         * NullPointerException이 발생한다.
         * set 메서드 없이 빈 등록이 안 되기 때문이다.
         * 생성자 주입을 적용하면 테스트를 짤때 파라미터가 뭘 필요로 하는지 바로 알 수 있다.
         */
//        OrderServiceImpl orderService = new OrderServiceImpl();
//        orderService.createOrder(1L, "itemA", 10000);

        MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
        memoryMemberRepository.save(new Member(1L, "userA", Grade.VIP));

        FixDiscountPolicy fixDiscountPolicy = new FixDiscountPolicy();

        OrderServiceImpl orderService = new OrderServiceImpl(memoryMemberRepository, fixDiscountPolicy);
        Order order = orderService.createOrder(1L, "itemA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}

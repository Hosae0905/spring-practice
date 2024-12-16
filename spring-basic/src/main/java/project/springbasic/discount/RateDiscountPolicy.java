package project.springbasic.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import project.springbasic.annotation.MainDiscountPolicy;
import project.springbasic.member.Grade;
import project.springbasic.member.Member;

@Component
//@Qualifier("mainDiscountPolicy")        // 빈 이름이 중복되는 것을 막는 방법
//@Primary        // 빈 이름이 중복되도 해당 애노테이션이 붙어있으면 우선권을 가지게 된다.
@MainDiscountPolicy     // 애노테이션을 직접 만들어서 사용할 수 있다. --> 오타가 나면 컴파일 오류로 확인할 수 있음
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}

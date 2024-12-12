package project.springbasic.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import project.springbasic.AppConfig;
import project.springbasic.member.MemberRepository;
import project.springbasic.member.MemberServiceImpl;
import project.springbasic.order.OrderServiceImpl;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {


        /**
         * [@Configuration이 있을 경우]
         * call AppConfig.memberService
         * call AppConfig.memberRepository
         * call AppConfig.orderService
         *
         * [@Configuration이 없을 경우]
         * call AppConfig.memberService
         * call AppConfig.memberRepository
         * call AppConfig.memberRepository
         * call AppConfig.orderService
         * call AppConfig.memberRepository
         */

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService --> memberRepository1 = " + memberRepository1);
        System.out.println("orderService --> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        assertThat(memberRepository1).isSameAs(memberRepository2);
        assertThat(memberRepository1).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        // AppConfig$$SpringCGLIB$$0 이렇게 출력된다.
        System.out.println("bean.getClass() = " + bean.getClass());
    }
}

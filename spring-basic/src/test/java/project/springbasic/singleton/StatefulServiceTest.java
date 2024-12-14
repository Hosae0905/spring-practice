package project.springbasic.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

//        //TheadA: A사용자가 10000원 주문
//        statefulService1.order("userA", 10000);
//        //TheadB: B사용자가 10000원 주문
//        statefulService2.order("userB", 20000);

        //TheadA: A사용자가 10000원 주문
        int userAPrice = statefulService1.order("userA", 10000);
        //TheadB: B사용자가 10000원 주문
        int userBPrice = statefulService2.order("userB", 20000);


//        //ThreadA: 사용자A가 주문 금액 조회
//        int price = statefulService1.getPrice();
//        System.out.println("price = " + price);     // 만원이 아닌 2만원이 나온다.

        /**
         * 같은 인스턴스를 공유하기 때문에 중간에 다른 사람의 가격으로 바뀌게 된다.
         */
//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);

        System.out.println("userAPrice = " + userAPrice);
        System.out.println("userBPrice = " + userBPrice);
        assertThat(userAPrice).isNotEqualTo(userBPrice);
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}

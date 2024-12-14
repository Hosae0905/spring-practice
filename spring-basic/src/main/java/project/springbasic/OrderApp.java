package project.springbasic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import project.springbasic.member.Grade;
import project.springbasic.member.Member;
import project.springbasic.member.MemberService;
import project.springbasic.member.MemberServiceImpl;
import project.springbasic.order.Order;
import project.springbasic.order.OrderService;
import project.springbasic.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl();
//        OrderService orderService = new OrderServiceImpl();
//
//        Long memberId = 1L;
//        Member memberA = new Member(memberId, "memberA", Grade.VIP);
//        memberService.join(memberA);
//
//        Order order = orderService.createOrder(memberId, "itemA", 10000);
//
//        System.out.println("order = " + order);
//        System.out.println("order.calculatePrice() = " + order.calculatePrice());


        /**
         * AppConfig 클래스를 통한 의존성 주입 후 예제를 실행
         */

        /*AppConfig appConfig = new AppConfig();

        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        Long memberId = 1L;
        Member memberA = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(memberA);

        Order order = orderService.createOrder(memberId, "itemA", 20000);

        System.out.println("order = " + order);
        System.out.println("order.calculatePrice() = " + order.calculatePrice());*/

        /**
         * 스프링 적용 후
         */

        /*
         * AnnotationConfigApplicationContext의 파라미터로 설정 정보가 담긴 AppConfig 클래스를 넣으면
         * @Bean이 붙은 것들을 모두 생성하여 스프링 컨테이너에 넣어준다.
         * */
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // 기본적으로 메서드 이름으로 등록된다.
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Long memberId = 1L;
        Member memberA = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(memberA);

        Order order = orderService.createOrder(memberId, "itemA", 20000);

        System.out.println("order = " + order);
        System.out.println("order.calculatePrice() = " + order.calculatePrice());
    }
}

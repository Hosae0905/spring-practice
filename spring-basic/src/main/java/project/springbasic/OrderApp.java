package project.springbasic;

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

        AppConfig appConfig = new AppConfig();

        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        Long memberId = 1L;
        Member memberA = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(memberA);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
        System.out.println("order.calculatePrice() = " + order.calculatePrice());
    }
}

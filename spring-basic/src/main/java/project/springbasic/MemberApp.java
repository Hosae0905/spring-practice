package project.springbasic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import project.springbasic.member.Grade;
import project.springbasic.member.Member;
import project.springbasic.member.MemberService;
import project.springbasic.member.MemberServiceImpl;

public class MemberApp {
    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl();      // 역할과 구현 모두 의존하고 있음
//        Member memberA = new Member(1L, "memberA", Grade.VIP);
//        memberService.join(memberA);
//
//        Member findMember = memberService.findMember(1L);
//        System.out.println("memberA.getName() = " + memberA.getName());
//        System.out.println("findMember = " + findMember.getName());

        /**
         * AppConfig 클래스를 통한 의존성 주입 후 예제를 실행
         */
        /*AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);

        Member member = memberService.findMember(1L);
        System.out.println("member.getName() = " + member.getName());
        System.out.println("memberA.getName() = " + memberA.getName());*/

        /**
         * 스프링 적용 후
         */

        /*
        * AnnotationConfigApplicationContext의 파라미터로 설정 정보가 담긴 AppConfig 클래스를 넣으면
        * @Bean이 붙은 것들을 모두 생성하여 스프링 컨테이너에 넣어준다.
        * */
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // 기본적으로 메서드 이름으로 등록된다. --> 자바의 리플렉션에 대해서 알아보기
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);

        Member member = memberService.findMember(1L);
        System.out.println("member.getName() = " + member.getName());
        System.out.println("memberA.getName() = " + memberA.getName());
    }
}

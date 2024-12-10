package project.springbasic;

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
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);

        Member member = memberService.findMember(1L);
        System.out.println("member.getName() = " + member.getName());
        System.out.println("memberA.getName() = " + memberA.getName());
    }
}

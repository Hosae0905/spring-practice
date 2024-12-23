package project.springbasic.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();     // 역할과 구현 모두 의존하고 있음
    private final MemberRepository memberRepository;

    /**
     *
     * @param memberRepository
     * 애플리케이션 구성 정보를 담고 있는 AppConfig 클래스를 통해서 객체의 생성과 주입을 맡기게 되었다.
     * 이를 통해서 오로지 MemberRepository 라는 추상화에만 의존하게 되므로써 실제 구현 객체가 바뀌어도 큰 문제가 되지 않는다.
     * 즉, DIP와 OCP를 지킬 수 있게 되었다.
     */
//    public MemberServiceImpl(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * @Component를 붙이게 되면서 생성자 주입을 자동으로 할 수 있게 해줘야 한다.
     * ac.getBean(MemberRepository.class) 이게 자동으로 들어간다고 생각하자.
     */
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}

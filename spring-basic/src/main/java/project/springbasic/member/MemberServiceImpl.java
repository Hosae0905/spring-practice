package project.springbasic.member;

public class MemberServiceImpl implements MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();     // 역할과 구현 모두 의존하고 있음
    private final MemberRepository memberRepository;

    /**
     *
     * @param memberRepository
     * MemberRepository에 대한 내용은 없어지고 오로지 MemberRepository라는 인터페이스만 있다.
     * 즉, 추상화에만 의존하게 되면서 DIP를 지킬 수 있다.
     */
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

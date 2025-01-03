package project.springmvc.domain.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.springmvc.domain.member.Member;
import project.springmvc.domain.member.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        /*Optional<Member> findMember = memberRepository.findByLoginId(loginId);
        Member member = findMember.get();
        if (member.getPassword().equals(password)) {
            return member;
        } else {
            return null;
        }*/

        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}

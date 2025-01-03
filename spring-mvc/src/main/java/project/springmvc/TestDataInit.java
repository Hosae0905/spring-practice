package project.springmvc;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.springmvc.domain.item.Item;
import project.springmvc.domain.item.ItemRepository;
import project.springmvc.domain.member.Member;
import project.springmvc.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

        Member member = new Member();
        member.setLoginId("test01");
        member.setPassword("qwer1234");
        member.setName("test");

        memberRepository.save(member);
    }

}

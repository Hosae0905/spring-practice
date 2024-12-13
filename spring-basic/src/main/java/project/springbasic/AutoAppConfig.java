package project.springbasic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import project.springbasic.member.MemberRepository;
import project.springbasic.member.MemoryMemberRepository;

@Configuration
@ComponentScan(
//        basePackages = "project.springbasic.member",      // 컴포넌트 스캔을 할 범위를 지정할 수 있다.
        // AppConfig를 통해서 빈이 생성되면 충돌이 발생하기 때문에 제외해준다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    /**
     * 수동 빈 vs 자동 빈
     * 이름이 같은 빈을 등록하면 오류가 발생하는지 확인 테스트
     * 오류는 발생하지 않고 오버라이딩을 하여 수동 빈이 우선권을 가진다.
     */
//    @Bean(name = "memoryMemberRepository")
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}

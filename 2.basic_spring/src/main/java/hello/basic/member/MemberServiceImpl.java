package hello.basic.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 의존관계가 인터페이스 뿐만 아니라 구현까지 모두 의존하는 문제가 있음.

    private final MemberRepository memberRepository;

/*    AutoAppConfig에 아무것도 없는데 MemberRepository 의존성을 어떻게 주입하지? -> @Autowired
    AppConfig에서는 @Bean 으로 직접 설정 정보를 작성했고, 의존관계도 직접 명시했다. 이제는 이런 설
    정 정보 자체가 없기 때문에, 의존관계 주입도 이 클래스 안에서 해결해야 한다.*/
    @Autowired // ac.getBean(MemberRepository.class)
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
/*    설계 변경으로 MemberServiceImpl 은 MemoryMemberRepository 를 의존하지 않는다!
    단지 MemberRepository 인터페이스만 의존한다.
    MemberServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
    MemberServiceImpl 의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부( AppConfig )에서 결정된다.

    DIP 완성: MemberServiceImpl 은 MemberRepository 인 추상에만 의존하면 된다. 이제 구체 클래스를 몰라도 된다.
    관심사의 분리: 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리되었다.
*/


    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}

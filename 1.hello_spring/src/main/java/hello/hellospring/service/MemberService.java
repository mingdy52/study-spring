package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    // memberServiceTest 의 memberRepository랑 다른 인스턴스. 같은 인스턴스를 쓰려면
    private final MemberRepository memberRepository;
/*
    final은 놔두고 외부에서 memberRepository를 생성하도록 만듬. Test beforeEach 참고
    MemberService 입장에서 memberRepository를 직접 new로 객체를 생성하지 않고
    외부에서 memberRepository 객체를 넣어줌. 이런 것을 DI(Dependency Injection, 의존성 주입) 이라고 함.
*/



    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* 회원 가입 */
    public Long join(Member member){
        // 가입을 할 때 같은 이름이 있으면 안된다 가정

        // Optional<Member> result = memberRepository.findByName(member.getName());
        // 옵셔널로 반환하는 것은 권장하지 않음. 아래 코드 참조.
        /*
            // 어차피 반환결과는 옵셔널 멤버니까 ifPresent() 사용 가능.
            memberRepository.findByName(member.getName())
                    .ifPresent(m -> {
                        // 객체 상태가 메서드 호출을 처리하기에 적절치 않을 때
                        throw new IllegalStateException("이미 존재하는 회원입니다.");
                    });
     */


        // 값이 있으면 꺼내고 없으면 ~~해
        //r esult.orElseGet();

        /* 값이 있으면
        result.ifPresent(m -> {
            // 객체 상태가 메서드 호출을 처리하기에 적절치 않을 때
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        */

        long start = System.currentTimeMillis();

        try {
            validateDuplicateMember(member); // 중복 회원 검증
            memberRepository. save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            System.out.println( "join = " + timeMs);
        }

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    // 객체 상태가 메서드 호출을 처리하기에 적절치 않을 때
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /* 전체 회원 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /*
        AOP: Aspect Oriented Programming
        공통 관심 사항과 핵심 관심 사항을 분리.
        - 공통 관심 사항을 분리해서 원하는 곳에 적용함. ex, 시간 측정 로직
    */
}

package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class MemoryMemberRepositoryTest {
    // 테스트 메소드 실행 순서는 랜덤

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        // 메소드 하나가 끝날 때마다 이 동작을 수행함.
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("Spring");

        repository.save(member);

        // 옵셔널에서 값을 꺼낼때는 get을 사용한다.
        Member result = repository.findById(member.getId()).get();

        //  junit
        // 같으면 초록불, 다르면 오류 값을 알려줌
        // Assertions.assertEquals(member, result);

        // assertj
        Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("Spring1");
        repository.save(member1);

        // shift + f6
        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        // get 사용해서 옵셔널 제거
        Member result = repository.findByName("Spring1").get();

        Assertions.assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("Spring1");
        repository.save(member1);

        // shift + f6
        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);

    }
}

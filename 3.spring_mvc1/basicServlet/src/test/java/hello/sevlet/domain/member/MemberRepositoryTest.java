package hello.sevlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {
    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Member member = new Member("hello", 20);

        // when
        Member save = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(save.getId());

        Assertions.assertThat(findMember).isEqualTo(save);

    }

    @Test
    void findAll() {
        // given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);

        Member save1 = memberRepository.save(member1);
        Member save2 = memberRepository.save(member2);

        // when
        List<Member> result = memberRepository.findAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(member1, member2);

    }


    // given

    // when

    //then
}
package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    // 회원 저장소에 저장
    Member save(Member member);

    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);

    // 모든 회원 리스트 반환
    List<Member> findAll();

    /*
        Optional : 널처리 방법.
        아이디나 이름이 널일 경우 널을 그대로 반환하지 않고 옵셔널로 감싸서 반환함.
    */


}

package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Optional;
import java.util.function.Function;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // 인터페이스가 인터페이스를 받을 때는 extends
    // JpaRepository<Member, 식별자>
    // JpaRepository 를 상속받으면 구현체를 자동으로 만들어서 스프링 빈도 자동으로 만들어줌.

    @Override
    Optional<Member> findByName(String name);
    /*
        findBy~~ 규칙 뒤에 컬림 이름을 쓰면 됨,
        select m from Member m where m.name = ?
        - findByNameAndId(String name, Long id)

        복잡한 동적 쿼리는 Querydsl이라는 라이브러리를 사용하면 된다.
        Querydsl을 사용하면 쿼리도 자바 코드로 안전하게 작성할 수 있고, 동적 쿼리도 편리하게 작성할 수 있다.
        이 조합으로 해결하기 어려운 쿼리는 JPA가 제공하는 네이티브 쿼리를 사용하거나, 앞서 학습한 스프링 JdbcTemplate를 사용하면 된다.
    */
}

package hello.hellospring.repository;


import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    // 임시적으로 회원을 저장해 놓을 장소
    // 실무에서는 동시성 문제가 있을 수 있어서 공유되는 변수는 ConcurrentMap을 상요함.
    private static Map<Long, Member> store = new HashMap<Long, Member>();

    private static Long sequence = 0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 옵셔널로 감싸서 리턴을 하면 클라이언트에서 후처리 가능.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
        // 저장 공간에서 로프를 돌려 이름이 파라미터로 넘어온 이름과 같으면 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}

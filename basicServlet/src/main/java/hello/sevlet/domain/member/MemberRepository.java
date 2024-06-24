package hello.sevlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

    private MemberRepository() {}

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll () {
        return new ArrayList<>(store.values());
        // 스토어의 모든 밸류들을 리스트로 리턴
        // 밖에서 ArrayList 의 밸류를 조작해도 스토어의 밸류는 건들 수 없음.
    }

    public void clearStore() {
        store.clear();
    }

}

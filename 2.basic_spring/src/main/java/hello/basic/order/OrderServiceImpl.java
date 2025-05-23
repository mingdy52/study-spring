package hello.basic.order;

import hello.basic.annotation.MainDiscountPolicy;
import hello.basic.discount.DiscountPolicy;
import hello.basic.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
// RequiredArgsConstructor // final. 필수 값이 붙은 필드를 가지고 생성자를 만들어줌.
public class OrderServiceImpl implements OrderService {

//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
     /*
    할인 정책을 변경하려면 클라이언트인 OrderServiceImpl 코드를 고쳐야 한다.
    DIP: 주문서비스 클라이언트( OrderServiceImpl )는 DiscountPolicy 인터페이스에 의존하면서 DIP를
    지킨 것 같은데?
    클래스 의존관계를 분석해 보자. 추상(인터페이스) 뿐만 아니라 구체(구현) 클래스에도 의존하고 있다.
            추상(인터페이스) 의존: DiscountPolicy
    구체(구현) 클래스: FixDiscountPolicy , RateDiscountPolicy
    OCP: 변경하지 않고 확장할 수 있다고 했는데!
    지금 코드는 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 준다! 따라서 OCP를 위반한다.

    DIP 위반 추상에만 의존하도록 변경(인터페이스에만 의존)
    DIP를 위반하지 않도록 인터페이스에만 의존하도록 의존관계를 변경하면 된다.

    */
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final DiscountPolicy discountPolicy;
    private final MemberRepository memberRepository;
//  ->  누군가가 클라이언트인 OrderServiceImpl 에 DiscountPolicy 의 구현 객체를 대신 생성하고 주입해주어야 한다

    public OrderServiceImpl (MemberRepository memberRepository
            , /*@Qualifier("mainDiscountPolicy")*/  @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;

        /*
            fixDiscountPolicy, rateDiscountPolicy

            조회 대상 빈이 2개 이상일 때 해결 방법
            @Autowired 필드 명 매칭.
                1. 타입 매칭을 시도하고, 이때 여러 빈이 있으면
                2. 필드 명, 파라미터 명으로 빈 이름을 추가 매칭한다.
            @Qualifier @Qualifier끼리 매칭 빈 이름 매칭. 추가 구분자를 붙여주는 방법.
                1. @Qualifier끼리 매칭
                2. 빈 이름 매칭
                3. NoSuchBeanDefinitionException 예외 발생
                --  @Qualifier("mainDiscountPolicy") 를 못찾으면 mainDiscountPolicy라는 이름의 스프링 빈을 추가로 찾는다.
            @Primary 사용
                @Autowired 시에 여러 빈이 매칭되면 @Primary 가 우선권을 가진다. -> rateDiscountPolicy
                @Qualifier 의 단점은 주입 받을 때 다음과 같이 모든 코드에 @Qualifier 를 붙여주어야 한다는 점이다.
                @Primary 를 사용하면 이렇게 @Qualifier 를 붙일 필요가 없다

        */
    }

/*    설계 변경으로 OrderServiceImpl 은 FixDiscountPolicy 를 의존하지 않는다!
    단지 DiscountPolicy 인터페이스만 의존한다.
    OrderServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
    OrderServiceImpl 의 생성자를 통해서 어떤 구현 객체을 주입할지는 오직 외부( AppConfig )에서 결정한다.
    OrderServiceImpl 은 이제부터 실행에만 집중하면 된다.
  */

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}

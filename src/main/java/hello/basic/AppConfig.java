package hello.basic;

import hello.basic.discount.DiscountPolicy;
import hello.basic.discount.FixDiscountPolicy;
import hello.basic.discount.RateDiscountPolicy;
import hello.basic.member.MemberRepository;
import hello.basic.member.MemberService;
import hello.basic.member.MemberServiceImpl;
import hello.basic.member.MemoryMemberRepository;
import hello.basic.order.OrderService;
import hello.basic.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration // 스프링 컨테이너가 해당 클래스를 스프링의 설정 정보로 사용. 근데 없어도 빈 등록 가능.
public class AppConfig {
//    구현 객체를 생성하고, 연결하는 책임을 가지는 별도의 설정 클래스
//    AppConfig는 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다.
//    AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)해준다.

    /*
    @Bean memberService -> new MemberRepository
    @Bean orderService -> new MemberRepository, new RateDiscountPolicy
    각각 다른 2개의 MemoryMemberRepository 가 생성되면서 싱글톤이 깨지는 것 처럼 보인다. 스프링 컨테이너는 이 문제를 어떻게 해결할까?

    예상. call AppConfig.memberRepository 가 3번 호출될 것
    결과.
    call AppConfig.memberService
    call AppConfig.memberRepository
    call AppConfig.orderService
    */

    @Bean // 스프링 컨테이너에 등록
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

    /*
      만약 @Configuration 가 없다면?
      configurationTestDeep()
        call AppConfig.memberRepository
        call AppConfig.memberRepository
        call AppConfig.orderService
        call AppConfig.memberRepository
        -> MemberRepository가 총 3번 호출. 1번은 @Bean에 의해 스프링 컨테이너에 등록하기 위해서이고,
            2번은 각각 memberRepository() 를 호출하면서 발생한 코드

        bean = class hello.basic.AppConfig -> 순수한 AppConfig로 스프링 빈에 등록

      configurationTest() -> 테스트 실패. 각 memberRepository 인스턴스는 다름.
    */
}

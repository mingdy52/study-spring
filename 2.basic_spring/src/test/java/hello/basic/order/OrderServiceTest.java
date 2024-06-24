package hello.basic.order;

import hello.basic.AppConfig;
import hello.basic.member.Grade;
import hello.basic.member.Member;
import hello.basic.member.MemberService;
import hello.basic.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
//    MemberService memberService = new MemberServiceImpl();
//    OrderService orderService = new OrderServiceImpl();//
    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    void before() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }


    @Test
    void createOrder(){
        Long memberId  = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);

    }
}

package hello.basic;

import hello.basic.member.Grade;
import hello.basic.member.Member;
import hello.basic.member.MemberService;
import hello.basic.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
//      MemberService memberService = new MemberServiceImpl();

//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        // 얘가 모든 빈 객체들을 관리해줌(스프링 컨테이너라)
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // AppConfig 클래스에 안에 있는 설정 정보들을 스프링 컨테이너에 객체 생성해서 관리해줌.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
/*
        스프링 컨테이너는 @Configuration 이 붙은 AppConfig 를 설정(구성) 정보로 사용한다. 여기서 @Bean 이
        라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다. 이렇게 스프링 컨테이너에 등록된
        객체를 스프링 빈이라 한다.
        스프링 빈은 @Bean 이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다.
        이전에는 개발자가 필요한 객체를 AppConfig 를 사용해서 직접 조회했지만, 이제부터는 스프링 컨테이너를 통해서
        필요한 스프링 빈(객체)를 찾아야 한다. 스프링 빈은 applicationContext.getBean() 메서드를 사용해서 찾을 수 있다.
        기존에는 개발자가 직접 자바코드로 모든 것을 했다면 이제부터는 스프링 컨테이너에 객체를 스프링 빈으로 등록하고,
        스프링 컨테이너에서 스프링 빈을 찾아서 사용하도록 변경되었다.
*/

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);

        System.out.println("find member = " + findMember.getName());
        System.out.println("new member = " + member.getName());
    }
}

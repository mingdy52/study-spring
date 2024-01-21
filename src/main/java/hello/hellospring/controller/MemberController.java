package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    /*
        스프링이 시작될 때 스프링 컨테이너라는 통이 생기는데
        @Controller가 있으면 MemberController 객체를 생성해서 스프링에 넣으두고 관리해줌.
         = 스프링 컨테이너에서 스프링 빈이 관리된다.
    */

    private final MemberService memberService;

    @Autowired // 생성자 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass());
        /*
            스프링 컨테이너가 뜰 때 MemberController 가 생성되는데 이 때 이 생성자를 호출함.
            @Autowired 가 있으면 컨테이너에 있는 MemberService 를 가져와서 연결시켜줌.
            - @Autowired 를 통한 DI는 helloController , memberService 등과 같이 스프링이 관리하는 객체에서만 동작한다.
            - 스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않는다.

            MemberController 가 생성될 때 스프링 빈에 등록되어 있는 MemberService 객체를 가져와서 넣어줌.
            - DI(Defendency Injection)
            - 스프링 빈은 기본적으로 싱글톤으로 등록됨. 즉, 같은 스프링 빈이면 같은 인스턴스임.
            - DI에는 필드 주입, setter 주입, 생성자 주입 이렇게 3가지 방법이 있음.
            - 의존관계가 실행중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입을 권장.

            required a bean of type 'hello.hellospring.service.MemberService' that could not be found.
            - MemberService에 @Service 어노테이션을 걸어두지 않았을 때
        */
    }

/*
    스프링이 관리하게 되면 모두 스프링 컨테이너에 등록을 하고 컨테이너로부터 받아 쓰도록 해야함.
    즉, new 안돼. 왜냐면 사용할 때마다 객체를 만들면 다른 여러 컨트롤러들이 MemberService 를 가져다 쓸 수 있음.
    근데 회원 관리하는데 여러 객체를 생성할 필요가..?


    스프링 빈을 등록하는 두가지 방법
    1. 컴포넌트 스캔과 자동 의존관계 설정
        - @Component, 지금의 방법
            @Controller
            @Service
            @Repository

          객체는 main() 메소드, @SpringBootApplication 가 있는 패키지를 포함해서 그 하위들을 스프링이 빈으로 등록함.
          특별한 설정이 없는 이상 아무곳에 패키지를 만들고 컴포넌트 스캔을 한다고 다 객체가 주입되는건 아님.

          장점.

    2. 자바 코드로 직접 스프링 빈 등록
        @Configuration
        @Bean
        - SpringConfig 클래서 참조.
        - 실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용.
        - 그러나 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 직접 등록한다
}
*/

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}

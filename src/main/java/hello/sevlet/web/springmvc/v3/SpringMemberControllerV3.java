package hello.sevlet.web.springmvc.v3;

import hello.sevlet.domain.member.Member;
import hello.sevlet.domain.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    // @RequestMapping(value = "/new-form", method = RequestMethod.GET)
    @GetMapping
    public String newForm() {
        return "new-form";
    }

    // @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PostMapping
    public String save(@RequestParam("username") String username
            , @RequestParam("age") int age
            , Model model) {

        Member member = new Member(username, age);
        System.out.println("member " + member);
        memberRepository.save(member);

        model.addAttribute("member", member);
        return "save-result";

    }

    // @RequestMapping(value = "/new-form", method = RequestMethod.GET)
    @GetMapping
    public String members(Model model) {
        List<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);
        return "members";

    }

}

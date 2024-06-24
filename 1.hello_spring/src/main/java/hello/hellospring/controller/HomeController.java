package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // 정적 파일을 찾을 때는 컨테이너를 찾아보고 없으면 static 파일을 참음.
        return "home";
    }

}

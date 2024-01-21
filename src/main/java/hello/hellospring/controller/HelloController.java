package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String Hello(Model model) {
        model.addAttribute("data", "hello spring!!");

        // 모델을 hello 파일로 가져가서 랜더링 시켜라.
        // 기본적으로 스프링 부트는 resources/templates 아래에 있는 파일을 찾음.
        return "hello";

        //  cmd 빌드 참고 https://ottl-seo.tistory.com/21
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(name="name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";

        /*
            http://localhost:8080/hello-mvc 으로 요청하면 에러남.
            Required request parameter 'name' for method parameter
            @RequestParam("name")의 required는 기본값이 true 이기 때문.

         */
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;

        // @ResponseBody: http response body부에 이 데이터를 직접 넣어주겠다.
        // html 파일 유무에 상관없이 그냥 화면에 리턴값이 표시된다.
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;

        // 객체를 넘기면 json 구조로 넘어감.

        /*
            @ResponseBody 를 사용하면 HTTP의 BODY에 문자 내용을 직접 반환한다.
            viewResolver 대신에 HttpMessageConverter 가 동작
            기본 문자처리: StringHttpMessageConverter
            기본 객체처리: MappingJackson2HttpMessageConverter
            byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음
         */
    }
    static class Hello {
        // static 클래스로 만들면 클래스 안에서 클래스 사용 가능
        private String name;
        // private 이기 때문에 외부에서 바로 못꺼냄.
        // 그래서 getter/setter 가 필요.

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}

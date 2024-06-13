package hello.springmvc.basic.reponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello");
        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");
        return "response/hello";
    }

    /*
        String을 반환하는 경우 - View or HTTP 메시지
        @ResponseBody가 없으면 ViewResolver가 실행되어 뷰를 찾고, 렌더링
        @ResponseBody가 있으면 ViewResolver를 실행하지 않고, Http 바디에 직접 문자열을 입력
    */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
        /*
            너무 불명확해서 권장하지 않음
            - 메서드의 반환 타입이 void인 경우, 스프링은 기본적으로 요청 URL을 뷰 이름으로 사용한다.
                하지만 HttpServletResponse나 OutputStream/Writer와 같은 HTTP 메시지 바디를 처리하는 파라미터가 메서드에 있으면,
                이 메서드는 HTTP 응답을 직접 작성한다. 이 경우 스프링은 뷰를 찾거나 렌더링하지 않고,
                메서드 내에서 작성된 내용을 그대로 HTTP 응답으로 사용한다.
            - @Controller를 사용하고, HttpServletResponse, OutputStream(Writer) 같은
                HTTP 메시지 바디를 처리하는 파라미터가 없으면 요청 URL을 참고해서 논리 뷰 이름으로 사용한다.


        */
    }


}

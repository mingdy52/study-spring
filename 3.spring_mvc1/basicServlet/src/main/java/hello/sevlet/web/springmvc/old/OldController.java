package hello.sevlet.web.springmvc.old;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

@Component("/springmvc/old-controller") // 빈 이름을 url로 지정
public class OldController implements Controller {
    /**
     컨트롤러를 호출하는 2가지 방법
     1. HandlerMapping(핸들러 매핑)
         핸들러 매핑에서 이 컨트롤러를 찾을 수 있어야 한다.
         예) 스프링 빈의 이름으로 핸들러를 찾을 수 있는 핸들러 매핑이 필요하다.

         0 = RequestMappingHandlerMapping : 애노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
         1 = BeanNameUrlHandlerMapping : 스프링 빈의 이름으로 핸들러를 찾는다. url과 똑같은 스프링 빈을 찾음.
     2. HandlerAdapter(핸들러 어댑터)
         핸들러 매핑을 통해서 찾은 핸들러를 실행할 수 있는 핸들러 어댑터가 필요하다.
         예) Controller 인터페이스를 실행할 수 있는 핸들러 어댑터를 찾고 실행해야 한다.

         0 = RequestMappingHandlerAdapter : 애노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
         1 = HttpRequestHandlerAdapter : HttpRequestHandler 처리
         2 = SimpleControllerHandlerAdapter : Controller 인터페이스(애노테이션X, 과거에 사용) 처리

     핸들러 매핑도, 핸들러 어댑터도 모두 순서대로 찾고 만약 없으면 다음 순서로 넘어간다.
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return new ModelAndView("new-form");
    }

}

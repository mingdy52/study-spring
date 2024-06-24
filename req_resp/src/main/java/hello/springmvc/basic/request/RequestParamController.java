package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
public class RequestParamController {

    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");
    }

    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @ResponseBody 추가
     * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     */

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName
            , @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username
            , @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = false) String username
            , @RequestParam(required = false) Integer age) {
        /*
            age가 int로 선언되어 있어서 null을 받을 수 없다.
            따라서 Integer로 선언해야 한다.

            null과 ""는 같지 않다.
            null은 값이 없다는 의미이고, ""는 값이 있지만 비어있다는 의미.
         */
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username
            , @RequestParam(required = false, defaultValue = "-1") int age) {
        /*
            1. defaultValue를 설정하면, 값이 없을 때 기본 값을 설정할 수 있다.
            2. defaultValue는 빈 문자의("") 경우에도 적용된다.
         */
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Objects> paramMap) {
        /*
            파라미터를 Map이나 MultiValueMap으로 조회할 수 있다.
            @RequestParam Map
                Map(key=value)
            @RequestParam MultiValueMap
                MultiValueMap(key=[value1, value2, ...])
                ex) ?userIds=id1&userIds=id2 -> MultiValueMap("userIds"=[id1, id2])
         */
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        /*
            @ModelAttribute가 있으면
                1. HelloData 객체를 생성한다.
                2. 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다.
                3. 해당 프로퍼티의 setter를 호출해서 값을 바인딩한다.

            BindingException
                - 바인딩에 실패하면 BindingException이 발생한다.
                - BindingException이 발생하면 ModelAttribute 메서드 호출이 실패한다.
                - BindingException은 서블릿 예외로 처리된다.
         */
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        /*
            @ModelAttribute 생략 가능
                - String, int 같은 단순 타입 = @RequestParam
                - argument resolver로 지정해둔 타입 외에는 모두 @ModelAttribute로 인식
         */
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }


}

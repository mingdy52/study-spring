package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody = {}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("messageBody = {}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /*
        @RequestBody 객체 파라미터
            - @RequestBody 에 직접 만든 객체를 지정할 수 있다.

        HttpEntity, @RequestBody 를 사용하면  HttpMessageConverter 가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환해준다.
        HttpMessageConverter 는 문자 뿐만 아니라 JSON, XML 등 다양한 형태의 HTTP 메시지를 객체로 변환할 수 있다.
            - HttpEntity: HTTP 메시지 컨버터 적용 (응답에서도 사용 가능)
            - @RequestBody: HTTP 메시지 컨버터 적용 (요청에서만 사용 가능)

        @RequestBody는 생략 불가능
            스프링은 @ModelAttribute, @RequestParam 해당 생략시 다음과 같은 규칙을 적용한다.
                - String, int, Integer 같은 단순 타입 = @RequestParam
                - 나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)

        따라서 이 경우 HelloData에 @RequestBody를 생략하면 @ModelAttribute가 적용되어 HTTP 메시지 바디가 아니라 요청 파라미터를 처리하게 된다.

        주의
            Http 요청시에 content-type이 application/json 으로 지정되어 있어야 한다.
            그렇지 않으면 HttpMessageConverter 가 메시지 바디를 읽어들이지 못한다.

    */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> data) throws IOException {
        HelloData helloData = data.getBody();
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /**
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type: application/json)
     *
     * @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용(Accept: application/json)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data;
    }
}

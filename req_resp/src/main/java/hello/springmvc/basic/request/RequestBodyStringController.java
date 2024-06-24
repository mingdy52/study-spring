package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(
            HttpServletRequest request
            , HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        // String: 바이트 코드
        // 바이트 코드를 문자로 변환할 때는 인코딩 방식을 지정해주어야 한다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        response.getWriter().write("ok");

    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");

    }

    /*
        스프링 MVC는 다음 파라미터를 지원한다.
            InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
            OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
    */

    /**
     * HttpEntity: HTTP header, body 정보를 편리하게 조회
     *  - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
     *      - 요청 파라미터를 조회하는 기능과 관계 없음
     *      - 요청 파라미터: GET 쿼리 파라미터, POST Form 파라미터
     *  - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * 응답에서도 HttpEntity 사용 가능
     *  - 메시지 바디 정보 직접 반환(view 조회X)
     *  - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *  - 헤더 정보 포함
     *
     * HttpEntity를 상속받은 다음 객체들도 같은 기능을 제공한다.
     * - RequestEntity
     *      - HttpMethod, url 정보가 추가, 요청에서 사용
     * - ResponseEntity
     *      - HTTP 상태 코드 설정 가능, 응답에서 사용
     *      - return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        // HttpMessageConverter 사용
        // HttpMessageConverter가 'HttpEntity<String>' 를 보고 body에 있는 데이터가 String이라고 판단하고, 이를 사용한다.
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);
        return new HttpEntity<>("ok");
        // 첫 번째 파라미터: 바디 메시지
    }

    /*
        @RequestBody
            - @RequestBody를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회
            - 헤더 정보가 필요하다면 HttpEntity를 사용하거나 @RequestHeader를 사용하면 된다.
            - 요청 파라미터를 조회하는 @RequestParam, @ModelAttribute 과 전혀 관계가 없다.
            - view를 사용하지 않는다.

        요청 파라미터 vs HTTP 메시지 바디
            - 요청 파라미터를 조회하는 기능: @RequestParam, @ModelAttribute
        HTTP 메시지 바디를 직접 조회하는 기능: @RequestBody

        @RequestBody 사용 테스트
            - @ResponseBody를 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다.
            - view를 사용하지 않는다.
    */
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        log.info("messageBody={}", messageBody);
        return "ok";
    }




}

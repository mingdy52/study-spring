package hello.springmvc.basic.reponse;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@Slf4j
@Controller
//@RestController
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    /**
     * HttpEntity, ResponseEntity(Http Status 추가)
     * @return
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
        // ResponseEntity 는 HttpEntity를 상속 받았는데, HttpEntity는 HTTP 메시지의 헤더, 바디 정보를 가지고 있다.
        // ResponseEntity는 여기에 더해서 HTTP 응답 코드를 설정할 수 있다.
    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }


    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData  helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK);
        //  HTTP 메시지 컨버터를 통해서 JSON 형식으로 변환되어서 반환된다.
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData  helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
        // ResponseEntity는 HTTP 응답 코드를 설정할 수 있는데, @ResponseBody를 사용하면 이런 것을 설정하기 까다롭다.
        // @ResponseStatus(HttpStatus.OK)애노테이션을 사용하면 응답 코드도 설정할 수 있다.
    }

    // @ResponseStatus는 애노테이션이기 때문에 응답 코드를 동적으로 변경할 수 없다.
    // 프로그램 조건에 따라서 동적으로 변경하려면 ResponseEntity를 사용한다.

    /*
        @RestController
        @Controller 대신 @RestController을 사용하면 @ResponseBody를 생략할 수 있다.
        @RestController = @Controller + @ResponseBody
     */
}

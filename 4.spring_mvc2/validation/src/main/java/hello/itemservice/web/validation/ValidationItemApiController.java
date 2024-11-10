package hello.itemservice.web.validation;

import hello.itemservice.web.validation.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    /*
        API의 경우 3가지 경우를 나누어 생각해야 한다.
            - 성공 요청: 성공
            - 실패 요청: JSON을 객체로 생성하는 것 자체가 실패함
            - 검증 오류 요청: JSON을 객체로 생성하는 것은 성공했고, 검증에서 실패함

     */

    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {
        log.info("API 컨트롤러 호출");

        if(bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors = {}", bindingResult);
            return bindingResult.getAllErrors();
            /*
                ObjectError 와 FieldError를 반환한다.
                예시로 보여주기 위해서 검증 오류 객체들을 그대로 반환했다.
                실제 개발할 때는 이 객체들을 그대로 사용하지 말고,
                필요한 데이터만 뽑아서 별도의 API 스펙을 정의하고 그에 맞는 객체를 만들어서 반환해야 한다.
             */

        }

        log.info("성공 로직 실행");
        return form;
    }

    /*
        @ModelAttribute vs @RequestBody
            HTTP 요청 파리미터를 처리하는 @ModelAttribute 는 각각의 필드 단위로 세밀하게 적용된다.
            그래서 특정 필드에 타입이 맞지 않는 오류가 발생해도 나머지 필드는 정상 처리할 수 있었다.

            HttpMessageConverter는 @ModelAttribute와 다르게 각각의 필드 단위로 적용되는 것이 아니라, 전체 객체 단위로 적용된다.
            따라서 메시지 컨버터의 작동이 성공해서 ItemSaveForm 객체를 만들어야 @Valid @Validated 가 적용된다.

            @ModelAttribute
                - 필드 단위로 정교하게 바인딩이 적용된다.
                - 특정 필드가 바인딩 되지 않아도 나머지 필드는 정상 바인딩 되고, Validator를 사용한 검증도 적용할 수 있다.
            @RequestBody
                - HttpMessageConverter 단계에서 JSON 데이터를 객체로 변경하지 못하면 이후 단계 자체가 진행되지 않고 예외가 발생한다.
                - 컨트롤러도 호출되지 않고, Validator도 적용할 수 없다.

            참고
                HttpMessageConverter 단계에서 실패하면 예외가 발생한다.
                예외 발생시 원하는 모양으로 예외를 처리하는 방법은 예외 처리 부분에서 다룬다.
     */

}

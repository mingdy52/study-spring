package hello.itemservice.validation;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
    // 에러 코드를 하나 넣으면 여러 값을 반환해준다.

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        for (String messageCode : messageCodes) {
            System.out.println(messageCode);
        }

        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        for (String messageCode : messageCodes) {
            System.out.println(messageCode);
        }

        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );

        /*
            BindingResult가 내부적으로 resolveMessageCodes(...) 를 호출함.
            => bindingResult.rejectValue("itemName", "required");

            new FieldError("item", "itemName", null, false, messageCodes, null, null);
                FieldError , ObjectError 의 생성자를 보면, 오류 코드를 하나가 아니라 여러 오류 코드를 가질 수 있다.
                MessageCodesResolver 를 통해서 생성된 순서대로 오류 코드를 보관한다.

            FieldError rejectValue("itemName", "required")
                다음 4가지 오류 코드를 자동으로 생성
                    required.item.itemName
                    required.itemName
                    required.java.lang.String
                    required

            ObjectError reject("totalPriceMin")
                다음 2가지 오류 코드를 자동으로 생성
                    totalPriceMin.item
                    totalPriceMin
        */
    }


        /*
            MessageCodesResolver
                검증 오류 코드로 메시지 코드들을 생성한다.
                MessageCodesResolver 인터페이스이고 DefaultMessageCodesResolver 는 기본 구현체이다.
                주로 다음과 함께 사용 ObjectError , FieldError

            DefaultMessageCodesResolver의 기본 메시지 생성 규칙
               객체 오류
                    객체 오류의 경우 다음 순서로 2가지 생성
                    1.: code + "." + object name
                    2.: code
                    예) 오류 코드: required, object name: item
                    1.: required.item
                    2.: required
                필드 오류
                    필드 오류의 경우 다음 순서로 4가지 메시지 코드 생성
                    1.: code + "." + object name + "." + field
                    2.: code + "." + field
                    3.: code + "." + field type
                    4.: code

                    예) 오류 코드: typeMismatch, object name "user", field "age", field type: int
                    1. "typeMismatch.user.age"
                    2. "typeMismatch.age"
                    3. "typeMismatch.int"
                    4. "typeMismatch"
        */
}

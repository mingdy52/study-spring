package hello.basic.scan.filter;

import java.lang.annotation.*;

@Target({ElementType.TYPE}) // type = 클래스 레벨
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}

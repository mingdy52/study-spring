package hello.basic.scan.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFIlterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        assertThat(beanA).isNotNull();

        // BeanB beanB = ac.getBean("beanB", BeanB.class);
        // 빈에 없으니까 조회하는 순간 exception

        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class)
        );
    }

    @Configuration
    @ComponentScan(
            includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
    )
    static class ComponentFIlterAppConfig {
/*
        ANNOTATION: 기본값, 애노테이션을 인식해서 동작한다.
        ex) org.example.SomeAnnotation
        ASSIGNABLE_TYPE: 지정한 타입과 자식 타입을 인식해서 동작한다.
        ex) org.example.SomeClass
        ASPECTJ: AspectJ 패턴 사용
        ex) org.example..*Service+
        REGEX: 정규 표현식
        ex) org\.example\.Default.*
        CUSTOM: TypeFilter 이라는 인터페이스를 구현해서 처리
        ex) org.example.MyTypeFilter


        예를 들어서 BeanA도 빼고 싶으면 다음과 같이 추가하면 된다.
        @ComponentScan(
            includeFilters = {@Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class)},
            excludeFilters = {@Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class),
                              @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BeanA.class)
            }
        )
*/

    }
}

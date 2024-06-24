package hello.basic.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA: A사용자가 10000원 주문
        int userAPrice = statefulService1.order("userA", 10000);
        //ThreadA: B사용자가 20000원 주문
        statefulService2.order("userB", 20000);

        // ThreadA: 사용자A 주문 금액 조회
        //int price = statefulService1.getPrice();
        System.out.println("price = " + userAPrice);

        //Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }

        /*
        우선, 자바의 static키워드가 어떻게 동작하는지 아셔야 합니다.
        static을 뺀 inner class는 inner class를 감싸고 있는 outer class에 종속됩니다.
        즉, outer class의 객체를 통해서만 inner class에 접근할 수 있습니다.
        static inner class는 outer class 내부에 선언되었지만 outer class의 객체 생성 유무와 별개로 만들어집니다. 독립적으로 사용되어지는 것이죠.
        */
    }

}
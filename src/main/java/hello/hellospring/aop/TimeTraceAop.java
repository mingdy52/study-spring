package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.swing.text.Style;

@Aspect
// @Component
public class TimeTraceAop {

    // @Around 로 aop를 구동하면 순환참조 문제가 발생함.
    // aop 를 생성하는 exucute() 메소드도 aop 로 처리하게 되기 때문인데
    // 이게 바로 TimeTraceAop 를 생성하는 코드여서 순환참조 문제가 발생함. aop 대상에서 SpringConfig를 빼서 해결할 수 있음.
    @Around("execution(* hello.hellospring..*(..)) && !target(hello.hellospring.SpringConfig)")
    public Object exucute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("Start: " + joinPoint.toString());
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            System.out.println("End: " + joinPoint.toString() + " " + timeMs + "ms");

        }

    }

    /*
        aop 설정을 하면 컨테이너에 스프링 빈을 등록할 때 가짜 memberService(프록시) 를 만듬.
        가짜 스프링 빈이 끝나면 그 때 진짜 service를 실행함.
    */
}

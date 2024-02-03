package hello.basic;

import hello.basic.member.MemberRepository;
import hello.basic.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan ( // @Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다.
        basePackages = "hello.basic.member",
        // member 패키지 아래만 컴포넌트 스캔의 대상이 된다.
        // 모든 자바코드, 라이브러리를 뒤지기 때문에.. 너무 많아..
        // basePackages = {"hello.core", "hello.service"} 여러개를 지정할 수도 있음.
        basePackageClasses = AutoAppConfig.class,
        // 지정한 클래스의 패키지를 탐색 시작 위치로 지정한다
        // base를 지정하지 않으면 @ComponentScan가 붙은 클래스의 패키지부터 그 하위 패키지까지 모두 탐색한다.

       /* 권장하는 방법
        패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것.
        hello.basic 프로젝트 시작 루트, 여기에 AppConfig 같은 메인 설정 정보를 두고, @ComponentScan 애노테이션을 붙이고, basePackages 지정은 생략한다.
        이렇게 하면 hello.basic 를 포함한 하위는 모두 자동으로 컴포넌트 스캔의 대상이 된다.
        그리고 프로젝트 메인 설정 정보는 프로젝트를 대표하는 정보이기 때문에 프로젝트 시작 루트 위치에 두는 것이 좋다 생각한다.
        참고로 스프링 부트를 사용하면 스프링 부트의 대표 시작 정보인 @SpringBootApplication 를 이 프로젝트 시작
        루트 위치에 두는 것이 관례이다. (그리고 이 설정안에 바로 @ComponentScan 이 들어있다!

        @SpringBootApplication 애노테이션이 내부적으로 @Configuration, @EnableAutoConfiguration, @ComponentScan을 가지고 있기 때문에
        각 스프링 부트 구동시 애플리케이션에 필요한 설정과 함께 스프링 컨테이너를 실행하고 스프링 빈을 자동으로 등록한다.
        컴포넌트 스캔이 여러 개일 경우 설정을 병합해서 실행하기 때문에 컴포넌트 스캔은 한 번만 실행된다.
        이와는 별개로 컴포넌트 스캔을 통한 빈 등록과 @Bean을 통한 수동 등록을 같이 할 때는 스프링 빈을 중복으로 등록하게 되어 빈 충돌 오류가 발생하게 됩니다.

        */
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // 빈 등록 제외할 것 지정
        // @Configuration 붙은 것을 제외. 이전에 학습했던 AppConfig 와 충돌을 피하기 위함. 2번 등록되고 실행될 것.
        // Configuration.class 에도 @Component 가 붙어있어 스캔의 대상이 됨.
        // includeFilters : 컴포넌트 스캔 대상을 추가로 지정한다.
        // excludeFilters : 컴포넌트 스캔에서 제외할 대상을 지정한다.


)

public class AutoAppConfig {

    /*
    @Bean(name = "memoryMemberRepository")
    public MemberRepository MemberRepository() {
        return new MemoryMemberRepository();
    }


    이 경우 수동 빈 등록이 우선권을 가진다. (수동 빈이 자동 빈을 오버라이딩 해버린다.)
    최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생하도록 기본 값을 바꾸었다
    테스트에서는 오류가 안나지만 BasicApplication 돌려보면 오류남.
    Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true
    application.properties에 setting spring.main.allow-bean-definition-overriding=true 등록하면 중복 가능
    */

/*
    @ComponentScan 은 @Component 가 붙은 모든 클래스를 스프링 빈으로 등록한다.
    이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
        빈 이름 기본 전략: MemberServiceImpl 클래스 memberServiceImpl
        빈 이름 직접 지정: 만약 스프링 빈의 이름을 직접 지정하고 싶으면
                        @Component("memberService2") 이런식으로 이름을 부여하면 된다.

    생성자에 @Autowired 를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입한다.
        -- Autowiring by type from bean name 'orderServiceImpl' via constructor to bean named 'memoryMemberRepository'
        -- Autowiring by type from bean name 'orderServiceImpl' via constructor to bean named 'rateDiscountPolicy'
    이때 기본 조회 전략은 타입이 같은 빈을 찾아서 주입한다.
        getBean(MemberRepository.class) 와 동일하다고 이해하면 된다.

    컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함한다.
    @Component : 컴포넌트 스캔에서 사용
    @Controller : 스프링 MVC 컨트롤러에서 사용
    @Service : 스프링 비즈니스 로직에서 사용
    @Repository : 스프링 데이터 접근 계층에서 사용
    @Configuration : 스프링 설정 정보에서 사용

    @Configuration, @Controller, @Service, @Repository 등은 모두 @Component 애노테이션을 구현하고 있다.
    그래서 이 중 어떤 애노테이션을 붙이더라도 컴포넌트 스캔 대상이 되며 빈으로 등록된다.
    다만, @Component 애노테이션을 다시 @Controller, @Configuration 등으로 나눈 것은
    각 애노테이션 별 고유의 기능을 추가함 혹은 컴포넌트의 역할을 명확히 함 등의 목적으로 한다.
    그래서 설정과 관련한 파일이라면 @Configuration을, 컨트롤러 역할을 하는 클래스는 @Controller 등을 붙여 사용하는것이 좋다.
    이도 저도 아닌 사용자가 정의한 임의의 객체라면 @Component 애노테이션을 사용하여 스프링 빈으로 등록할 수 있습니다 .

     **
        애노테이션에는 상속관계라는 것이 없다. 그래서 이렇게 애노테이션이 특정 애노테이션을 들고 있는 것을 인식할 수 있는 것은
        자바 언어가 지원하는 기능은 아니고, 스프링이 지원하는 기능이다.
     **

     컴포넌트 스캔의 용도 뿐만 아니라 다음 애노테이션이 있으면 스프링은 부가 기능을 수행한다.
        @Controller : 스프링 MVC 컨트롤러로 인식
        @Repository : 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링 예외로 변환해준다.
        @Configuration : 앞서 보았듯이 스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리를 한다.
        @Service : 사실 @Service 는 특별한 처리를 하지 않는다.
                   대신 개발자들이 핵심 비즈니스 로직이 여기에 있겠구나 라고 비즈니스 계층을 인식하는데 도움이 된다.
*/

}

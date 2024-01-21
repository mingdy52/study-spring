package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringApplication.class, args);

		/*
			HelloSpringApplication 클래스를 넣고 실행시시키면
			(@SpringBootApplication) 스프링 부트 어플리케이션이 실행
			톰캣 웹서버는 내장되어 있음.
		*/
	}

}

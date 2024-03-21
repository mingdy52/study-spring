package hello.sevlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan // 서블릿 자동 등록. 스프링이 현재 패키지에서 그 하위까지 서블릿을 등록해줌.
@SpringBootApplication
public class SevletApplication {

	public static void main(String[] args) {
		SpringApplication.run(SevletApplication.class, args);
	}

}

package hello.sevlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 네트워크를 통한 데이터 전송 시, 데이터는 바이트 형태로 전송되어야 하며, 이는 문자열 데이터를 포함한 모든 종류의 데이터가 바이트 배열로 변환되어야 함을 의미

        ServletInputStream inputStream = request.getInputStream();
        // 메시지 바디의 내용을 바이트 코드로 읽어옴.

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        // 바이트 코드를 스트링으로 바꿔줌.
        // 바이트를 문제로 변환할때는 항상 어떤 인코딩인지 알려줘야 한다.

        System.out.println("messageBody = " + messageBody);

        response.getWriter().write("ok");
    }
}

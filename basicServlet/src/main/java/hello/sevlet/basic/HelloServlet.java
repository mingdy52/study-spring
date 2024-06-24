package hello.sevlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    // HTTP 요청을 통해 매핑된 URL이 호출되면 서블릿 컨테이너는 다음 메서드를 실행한다.
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");

        System.out.println("request " + request);
        System.out.println("response " + response);
        // 여러 was 서버가 이 서블릿 표준 스펙을 구현 -> RequestFacade@293f6df1

        String username = request.getParameter("username");
        System.out.println("username " + username);

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        // 두개는 헤더 정보

        response.getWriter().write("hello " + username);
        // getWriter() = http 데이터 바디에 데이터를 넣어줌.
    }
    // http 요청이 오면 was가 서블릿 컨테이너에 request, response 객체를 만들어서 서블릿에 던져줌
    // /hello 를 호출하면 웹 브라우저가 요청메시지를 만들어서 서버에 던짐.
}

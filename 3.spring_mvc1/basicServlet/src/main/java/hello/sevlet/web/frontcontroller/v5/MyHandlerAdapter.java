package hello.sevlet.web.frontcontroller.v5;

import hello.sevlet.web.frontcontroller.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface MyHandlerAdapter {

    boolean supports(Object handler);

    ModelView handler(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}

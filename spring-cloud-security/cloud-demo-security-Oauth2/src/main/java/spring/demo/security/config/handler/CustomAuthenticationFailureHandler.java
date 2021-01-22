package spring.demo.security.config.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录失败逻辑
 * @author huazai
 * @date 2021/1/20 16:13
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final String url;

    public CustomAuthenticationFailureHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        //重定向
        response.sendRedirect(url);

    }
}

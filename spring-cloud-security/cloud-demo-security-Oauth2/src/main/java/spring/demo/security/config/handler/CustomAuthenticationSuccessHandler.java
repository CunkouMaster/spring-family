package spring.demo.security.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功逻辑
 * @author huazai
 * @date 2021/1/20 15:59
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final String url;

    public CustomAuthenticationSuccessHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //重定向
        response.sendRedirect(url);
    }
}

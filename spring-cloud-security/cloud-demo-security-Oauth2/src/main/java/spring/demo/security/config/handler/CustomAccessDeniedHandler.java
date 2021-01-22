package spring.demo.security.config.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义异常处理逻辑
 * @author huazai
 * @date 2021/1/20 19:09
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");

//        JSONObject error = new JSONObject();
//        error.put("code", HttpServletResponse.SC_FORBIDDEN);
//        error.put("message","权限不足");
//
//        PrintWriter writer = response.getWriter();
//        writer.write(error.toJSONString());
//        writer.flush();
//        writer.close();
    }
}

package spring.demo.security.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author huazai
 * @date 2021/1/29 11:03
 */
@RestController()
@RequestMapping("user")
public class UserController {

    @RequestMapping("getUserAuthentication")
    public Object getUserAuthentication(Authentication authentication, HttpServletRequest request){

        String header = request.getHeader("Authorization");

        String jwtToken = header.substring(header.indexOf("Bearer") + 7);

        //获取jwt内容
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey("xxx".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(jwtToken);

        return jws;
    }

}

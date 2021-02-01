package spring.demo.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huazai
 * @date 2021/1/29 11:03
 */
@RestController()
@RequestMapping("user")
public class UserController {

    @RequestMapping("getUserAuthentication")
    public Object getUserAuthentication(Authentication authentication){
        return authentication.getPrincipal();
    }

}

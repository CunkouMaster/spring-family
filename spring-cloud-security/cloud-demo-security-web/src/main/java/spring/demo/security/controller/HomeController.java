package spring.demo.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huazai
 * @date 2021/1/18 14:32
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(){
        return "success.html";
    }

}

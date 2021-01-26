package spring.demo.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huazai
 * @date 2021/1/18 14:32
 */
@Controller
public class HomeController {

    @RequestMapping("/csrfLogin")
    public String csrfLogin(){
        return "login";
    }

    @RequestMapping("/successPage")
    public String success(){
        // 重定向要写/标识 区分模版解析
        return "redirect:/success.html";
    }

    @RequestMapping("/errorPage")
    public String error(){
        return "redirect:/error.html";
    }

    @RequestMapping("/")
    public String index(){
        return "main";
    }


}

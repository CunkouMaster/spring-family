package spring.demo.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huazai
 * @date 2021/1/18 14:32
 */
@Controller
public class HomeController {

    //根据角色判断，必须以ROLE_开头，区分大小写
//    @Secured("ROLE_A")
    //执行之前判断，access表达式，ROLE_可加可不加
//    @PreAuthorize("hasRole('ROLE_B')")
    @RequestMapping("/successPage")
    public String success(){
        // 重定向要写/标识 区分模版解析
        return "redirect:/success.html";
    }

    @RequestMapping("/errorPage")
    public String error(){
        return "redirect:/error.html";
    }

    @RequestMapping("/main")
    public String index(){
        return "main";
    }

    @RequestMapping("/csrfLogin")
    public String csrfLogin(){
        return "login";
    }

}

package spring.demo.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author huazai
 * @date 2020/11/19 14:51
 */
@SpringBootApplication
public class Application {

    /**
     * 授权码模式
     * 1、登录 http://127.0.0.1:9012  用户admin 密码 123456
     * 2、获取授权码 http://127.0.0.1:9012/oauth/authorize?response_type=code&client_id=custom_root&redirect_uri=https://www.baidu.com/
     * 3、获取token
     *      post请求
     *      http://127.0.0.1:9012/oauth/token?client_id=custom_root&
     *                                      grant_type=authorization_code&
     *                                      code=565Xw0&
     *                                      redirect_uri=https://www.baidu.com/
     *      Authorization  添加 type【Basic Auth】，username【custom_root】， password【root】
     * 4、获取资源  http://127.0.0.1:9012/user/getUserAuthentication
     *       Authorization  添加 type【Bearer token】
     *
     *
     *
     * @param args
     */

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

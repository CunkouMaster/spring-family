package spring.demo.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import spring.demo.security.service.CustomUserDetailsService;

import javax.annotation.Resource;

/**
 * @author huazai
 * @date 2021/1/19 13:47
 */
@Configuration
//开启Security注解
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private CustomUserDetailsService customUserDetailsService;

//    @Resource
//    private PersistentTokenRepository persistentTokenRepository;





    /**
     *  用来配置 WebSecurity 。
     *  而 WebSecurity 是基于 Servlet Filter 用来配置 springSecurityFilterChain 。
     *  而 springSecurityFilterChain 又被委托给了 Spring Security 核心过滤器 Bean DelegatingFilterProxy 。
     *  相关逻辑你可以在 WebSecurityConfiguration 中找到。
     *  我们一般不会过多来自定义 WebSecurity , 使用较多的使其ignoring() 方法用来忽略 Spring Security 对静态资源的控制。
     * @param webSecurity
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        super.configure(webSecurity);
    }

    /**
     * 用来配置认证管理器AuthenticationManager。
     * 说白了就是所有 UserDetails 相关的它都管，包含 PasswordEncoder 密码机。
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    /**
     *  URL权限控制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*
            默认它提供了三种登录方式：
            formLogin() 普通表单登录
            oauth2Login() 基于 OAuth2.0 认证/授权协议
            openidLogin() 基于 OpenID 身份认证规范
            以上三种方式统统是 AbstractAuthenticationFilterConfigurer 实现的
         */

        //表单登录
        http.formLogin()
                //自定义登录页面
//                .loginPage("/login.html")
                .loginPage("/csrfLogin")
                //自定义登录逻辑【不是controller中的login，是UserDetailsService中的loadUserByUsername方法】
                .loginProcessingUrl("/login")
                //自定义 登录成功/失败 逻辑
                //认证成功/失败 跳转URL路径 【必须POST请求】
                .successForwardUrl("/successPage")
                .failureForwardUrl("/errorPage")
//                .successHandler(new CustomAuthenticationSuccessHandler("https://www.baidu.com/"))
//                .failureHandler(new CustomAuthenticationFailureHandler("https://cn.bing.com/"))
        ;

        http.authorizeRequests()
                //放行 登录页、error页面、退出成功页面
                .antMatchers("/login.html","/error.html","/csrfLogin").permitAll()
                .antMatchers("/logoutSuccess.html").permitAll()

                //所有请求都必须认证（登录）
                .anyRequest().authenticated()
        ;

        //退出登录
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logoutSuccess.html")
        //添加 /logout 能够以 GET 请求的配置
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
        ;

        // CSRF攻击拦截关闭
//        http.csrf().disable();
    }

}

package spring.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import spring.demo.security.config.handler.CustomAccessDeniedHandler;
import spring.demo.security.config.handler.CustomAuthenticationFailureHandler;
import spring.demo.security.config.handler.CustomAuthenticationSuccessHandler;
import spring.demo.security.service.CustomUserDetailsService;

import javax.annotation.Resource;
import javax.sql.DataSource;

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
    @Resource
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Resource
    private DataSource dataSource;
    @Resource
    private PersistentTokenRepository persistentTokenRepository;

    @Bean("passwordEncoder")
    public PasswordEncoder getPasswordEncoderInstance() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 持久化token
     *
     * Security中，默认是使用PersistentTokenRepository的子类InMemoryTokenRepositoryImpl，将token放在内存中
     * 如果使用JdbcTokenRepositoryImpl，会创建表persistent_logins，将token持久化到数据库
     */
    @Bean("persistentTokenRepository")
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        // 设置数据源
        tokenRepository.setDataSource(dataSource);
        // 启动创建表，创建成功后注释掉
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    /**
     *  URL权限控制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //表单登录
        http.formLogin()
                //自定义登录页面
                .loginPage("/login.html")
                //自定义登录逻辑【不是controller中的login，是UserDetailsService中的loadUserByUsername方法】
                .loginProcessingUrl("/login")
                //登陆请求参数自定义
                .usernameParameter("self_username")
                .passwordParameter("self_password")
                //认证成功/失败 跳转URL路径 【必须POST请求】
                .successForwardUrl("/successPage")
                .failureForwardUrl("/errorPage")
                //自定义 登录成功/失败 逻辑
//                .successHandler(new CustomAuthenticationSuccessHandler("https://www.baidu.com/"))
//                .failureHandler(new CustomAuthenticationFailureHandler("https://cn.bing.com/"))
        ;

        http.authorizeRequests()
                //放行 登录页、error页面、退出成功页面
                .antMatchers("/login.html","/error.html").permitAll()
                .antMatchers("/logoutSuccess.html").permitAll()
                /*
                    antMatchers -- ant表达式，有http请求方式参数
                    regexMatchers -- 正则表达式，有http请求方式参数

                    url 匹配规则
                    ?  : 匹配一个字符
                    *  : 匹配0或多个字符
                    ** : 匹配0或多个目录
                 */
                //访问路径控制
//                .mvcMatchers("/images/**").servletPath("/security-web").permitAll()
//                .regexMatchers(HttpMethod.GET,"").permitAll()
//                .antMatchers("/images/**").permitAll()
//                .antMatchers("/**/*.png").permitAll()
                //根据权限匹配【严格区分大小写】
//                .antMatchers("/index.html").hasAuthority("admin")
//                .antMatchers("/index.html").hasAnyAuthority("admin","ROLE_A")
                //根据角色匹配【不能以ROLE_开头，严格区分大小写】
//                .antMatchers("/index.html").hasRole("B")
//                .antMatchers("/index.html").hasAnyRole("A","B")
                //根据IP匹配
//                .antMatchers("/index.html").hasIpAddress("127.0.0.1")
                //自定义权限逻辑
//                .anyRequest().access("@customPermissionService.hasPermission(request,authentication)")

                //所有请求都必须认证（登录）
                .anyRequest().authenticated()
        ;

        //异常处理
        http.exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler);

        //登录记住
        http.rememberMe()
                //自定义登录逻辑
                .userDetailsService(customUserDetailsService)
                //指定存储位置
                .tokenRepository(persistentTokenRepository)
                //失效时间，默认2周
                .tokenValiditySeconds(60*60)
                //自定义参数
                .rememberMeParameter("self_remember-me")
                //自定义记住逻辑
//                .rememberMeServices()
        ;

        //退出登录
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logoutSuccess.html")
        ;

        // CSRF攻击拦截关闭
        http.csrf().disable();
    }
}

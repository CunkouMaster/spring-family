package spring.demo.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务配置
 *
 * tip: WebSecurityConfigurerAdapter与ResourceServerConfigurerAdapter同时在的话且都配置某个相同规则http路径，默认是后者会生效
 *
 * @author huazai
 * @date 2021/1/28 16:21
 */
@Configuration
@EnableResourceServer
public class CustomResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 资源安全配置
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }

    /**
     * http安全配置
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //规则
        http.authorizeRequests()
                .anyRequest().authenticated();

        // 配置需要保护的资源
        http.requestMatchers().antMatchers("/user/**");
    }
}

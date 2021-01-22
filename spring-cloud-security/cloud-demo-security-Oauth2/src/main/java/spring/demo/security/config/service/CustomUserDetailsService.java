package spring.demo.security.config.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 自定义登录逻辑
 * @author huazai
 * @date 2021/1/20 14:48
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("执行自定义登录逻辑");
        /*
            1 根据username查询数据库
            2 比较密码
            3 返回对象
         */
        if(!"admin".equals(username)){
            throw new UsernameNotFoundException("用户名错误");
        }

        //用户名 -- 加密密码 -- 用户权限
        return new User(
                "admin",
                passwordEncoder.encode("123456"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_A"));
    }
}

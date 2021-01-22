package spring.demo.security.config.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 自定义权限逻辑
 * @author huazai
 * @date 2021/1/20 19:27
 */
@Service
public class CustomPermissionService {

    public Boolean hasPermission(HttpServletRequest httpServletRequest,
                                 Authentication authentication){

        Object principal = authentication.getPrincipal();

        if(principal instanceof UserDetails){

            UserDetails userDetails = (UserDetails) principal;
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            return authorities.contains(new SimpleGrantedAuthority("normal"));
        }

        return false;
    }
}

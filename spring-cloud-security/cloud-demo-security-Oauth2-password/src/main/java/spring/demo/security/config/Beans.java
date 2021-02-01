package spring.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author huazai
 * @date 2021/1/27 15:47
 */
@Configuration
public class Beans {

    @Resource
    private DataSource dataSource;

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean(name = "CustomBCryptPasswordEncoder")
    public PasswordEncoder getPasswordEncoderInstance() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 持久化token
     *
     * Security中，默认是使用PersistentTokenRepository的子类InMemoryTokenRepositoryImpl，将token放在内存中
     * 如果使用JdbcTokenRepositoryImpl，会创建表persistent_logins，将token持久化到数据库
     */
    @Bean(name = "CustomPersistentTokenRepository")
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        // 设置数据源
        tokenRepository.setDataSource(dataSource);
        // 启动创建表，创建成功后注释掉
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Bean(name = "CustomClientDetailsService")
    public ClientDetailsService JdbcClientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }


    @Bean(name = "CustomRedisTokenStore")
    public TokenStore getRedisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
